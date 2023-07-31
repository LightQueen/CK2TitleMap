package com.example.accessingdatajpa.controller;

import com.example.accessingdatajpa.entity.*;
import com.example.accessingdatajpa.entity.Ch;
import com.example.accessingdatajpa.dao.ChRepository;
import com.example.accessingdatajpa.dao.DynastyRepository;
import com.example.accessingdatajpa.service.HongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="/demo")
@Slf4j
//@Scope("session")
public class MainController {

    @Autowired
    private ChRepository chRepository;
    @Autowired
    private HongService hongService;

    @GetMapping(path={"/prepare","/prepare/hong"})
    public @ResponseBody String prepareHong() {
        hongService.createHong();
        return "OK";
    }

    @GetMapping(path="/all-guests")
    public @ResponseBody List<Ch> allGuest() {
        Ch root = chRepository.findById("JiaMu").get();
        return hongService.allGuestsByCh(root);
    }

    @GetMapping(path="/realm-his")
    public @ResponseBody Collection<TitleDay> realmHis() {
        return hongService.realmWithDaysOfChCode("JiaBaoYu");
    }

    @GetMapping(path="/realm-depth")
    public @ResponseBody Map<Title, Integer> realmDepth(@Param("type") Integer type) {
        return hongService.realmWithDynastyDepthByDynCode("Jia",type);
    }

    @GetMapping(path="/test")
    public @ResponseBody String test() {
        hongService.test();

        return "OK";
    }
}
