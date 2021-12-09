package com.stellagosa.demo.converter;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum;
import com.github.houbb.pinyin.util.PinyinHelper;
import com.stellagosa.demo.converter.entity.City;
import com.stellagosa.demo.converter.entity.Wrap;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) throws IOException {
        List<City> cities = readCity("src/main/resources/city.json");
        cities.forEach(Demo::generateCharacter);
        Map<String, List<City>> map = cities.stream().collect(Collectors.groupingBy(City::getFirstLetter));
        map.replaceAll((c, v) -> sort(map.get(c)));
        map.forEach((k,v) -> v.forEach(Demo::resetCity));
        List<Wrap> wraps = map.entrySet().stream().map(entry -> {
            Wrap wrap = new Wrap();
            wrap.setTitle(entry.getKey());
            wrap.setGroups(entry.getValue());
            return wrap;
        }).collect(Collectors.toList());
        JSON json = JSONUtil.parse(wraps);
        FileWriter writer = new FileWriter("target.json");
        json.write(writer);
    }

    public static void resetCity(City city) {
        city.setCity(null);
        city.setArea(null);
        city.setLetter(null);
        city.setProvince(null);
        city.setTown(null);
        city.setFirstLetter(null);
    }

    public static List<City> sort(List<City> list) {
        return list.stream().sorted(Comparator.comparing(City::getLetter)).collect(Collectors.toList());
    }

    public static void generateCharacter(City city) {
        String letter = PinyinHelper.toPinyin(city.getName(), PinyinStyleEnum.FIRST_LETTER);
        letter = letter.strip().toUpperCase();
        city.setLetter(letter);
        city.setFirstLetter((String.valueOf(letter.charAt(0))));
    }

    public static List<City> readCity(String path) {
        JSON json = JSONUtil.readJSON(new File(path), StandardCharsets.UTF_8);
        return JSONUtil.toBean(json, new TypeReference<>() {}, false);
    }
}
