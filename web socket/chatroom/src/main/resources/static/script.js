let token

function loginButton() {
    let username = document.getElementById("inputUsername").value
    let password = document.getElementById("inputPassword").value

    if (username === '' || password === '') {
        console.log("用户名或密码不能为空")
    } else {
        let httpRequest = new XMLHttpRequest()
        httpRequest.open('POST', '/login', true)
        httpRequest.setRequestHeader('Content-type', 'application/json')
        httpRequest.send(JSON.stringify({'username': username, 'password': password}))
        httpRequest.onreadystatechange = () => {
            if (httpRequest.readyState === 4 && httpRequest.status === 200) {
                let data = httpRequest.responseText
                let res = JSON.parse(data)
                token = res.data
                register(username)
            }
        }
    }
}

let number = 1
let client

function register(username) {
    if (client && client.connected) return;
    let sock = new SockJS("/portfolio")
    client = Stomp.over(sock)
    client.connect(
        {
            'token': token
        },
        () => {
            console.log("连接成功！")
            client.subscribe("/topic/chatroom", (data) => {
                if (data) {
                    console.log(data)
                    showMsg(data.body, username)
                }
            })
            client.subscribe("/topic/userLogin", data => {
                if (data) {
                    console.log(data)
                    showUserLoginOrLogout(data.body, username, true)
                }
            })
            client.subscribe("/topic/userLogout", data => {
                if (data) {
                    console.log(data)
                    showUserLoginOrLogout(data.body, username, false)
                }
            })
            showUserOnlineOrNoOnline(true)
            getOnlineUsers(username)
        },
        (error) => {
            number++
            if (number < 3) {
                register()
            }
            console.log("连接失败")
            console.log(error)
        }
    )
}

function quit() {
    client.disconnect()
    showUserOnlineOrNoOnline(false)
}

function showMsg(data, username) {
    let res = JSON.parse(data)
    let showMsg = document.getElementById("show-msg")

    let container = document.createElement("div")
    container.className = 'container'

    let name = document.createElement("div")
    name.className = 'container-name'
    let label = document.createElement("label")
    label.className = username === res.from ? 'self-label' : 'other-label'
    label.innerText = res.from
    name.appendChild(label)

    let content = document.createElement("div")
    content.className = 'container-content'
    let text = document.createElement("div")
    text.className = username === res.from ? 'self-text' : 'other-text'
    text.innerText = res.msg
    content.appendChild(text)

    container.appendChild(name)
    container.appendChild(content)

    showMsg.appendChild(container)

    scrollToBottom()
}

function sendMsg() {
    let msg = document.getElementById("inputText").value
    client.send('/app/send', {'token': token}, msg)
}

function getOnlineUsers(username) {
    let httpRequest = new XMLHttpRequest()
    httpRequest.open('GET', '/user/allOnline', true)
    httpRequest.setRequestHeader('token', token)
    httpRequest.send(null)
    httpRequest.onreadystatechange = () => {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            let data = httpRequest.responseText
            let res = JSON.parse(data)
            showOnlineUsers(res.data, username)
        }
    }
}

function showOnlineUsers(data, username) {
    let onlineUsers = document.getElementById("online-users")
    onlineUsers.innerText = ""
    for (const item of data) {
        if (item === username) continue
        let onlineUser = document.createElement("div")
        onlineUser.innerText = item
        onlineUser.className = 'online-user'
        onlineUser.id = item
        onlineUser.onclick = () => onlineUserClick(item)
        onlineUsers.appendChild(onlineUser)
    }
}

function onlineUserClick(username) {
    console.log(username)
}

function showUserLoginOrLogout(data, username, flag) {
    getOnlineUsers(username)
    if (data === username) return
    let showMsg = document.getElementById("show-msg")
    let userLoginMsg = document.createElement("div")
    userLoginMsg.innerText = flag === true ? '用户' + data + '已登录' : '用户' + data + '已下线'
    userLoginMsg.className = 'user-login-or-logout-msg'
    showMsg.appendChild(userLoginMsg)

    scrollToBottom()
}

function showUserOnlineOrNoOnline(flag) {
    let isOnline = document.getElementById("is-online")
    isOnline.innerText = flag ? '已上线' : '不在线'
    let sendMsg = document.getElementById("sendMsg")
    sendMsg.disabled = !flag
}

function scrollToBottom() {
    let showMsg = document.getElementById("show-msg")
    let last = showMsg.lastChild
    showMsg.scrollTo({
        top: last.offsetTop,
        behavior: "smooth"
    })
}
