package com.ptt.controller;

import com.ptt.pojo.Bmb;
import com.ptt.service.BmbService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/bmb")
public class BmbController {

    Logger logger = Logger.getLogger(BmbController.class);

    @Autowired
    private BmbService bmbService;

    //查询全部
    @RequestMapping("/user/allBmb")
    public String list(Model model){
        logger.info("---------学员信息查询开始-------------");
        try {
            List<Bmb> list = bmbService.queryAllBmb();
            model.addAttribute("list",list);
            logger.info("=====查询成功==输出信息===");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("学员信息查询失败，打印信息："+e.getMessage(),e);
        }

        return "allBmb";
        //return "myView3";
    }



    //添加成员
    @RequestMapping("/toAddBmb")
    public String toAddPeople(){
        return "addBmb";
    }

    @RequestMapping(value = "/addBmb",method = RequestMethod.POST)
    public String addPeople(Bmb bmb){
        System.out.println(bmb);
        int i = bmbService.saveBmb(bmb);
        if(i>0){
            System.out.println("添加成功");
        }
        return "redirect:/user/allBmb";
    }
    //修改一条记录
    @RequestMapping("/toUpdateBmb")
    public String toUpdateBmb(Model model, int id){
        Bmb bmb = bmbService.getBmbById(id);
        System.out.println(bmb);
        model.addAttribute("Qbmb",bmb);
        return "updateBmb";
    }

    @RequestMapping("/updateBmb")
    public String updateBmb(Model model, Bmb bmb){
        System.out.println(bmb);
        int i = bmbService.updateBmb(bmb);
        if(i>0){
            System.out.println("更新成功");
        }
        Bmb bmb1 = bmbService.getBmbById(bmb.getId());
        model.addAttribute("bmb",bmb1);
        return "redirect:/user/allBmb";
    }
    //删除一本书籍
    @RequestMapping("/del/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        int i = bmbService.deleteBmbById(id);
        if(i>0){
            System.out.println("删除成功");
        }
        return "redirect:/user/allBmb";
    }

    //查询全部
    @RequestMapping("/allBmb1")
    @ResponseBody
    public Map MapBmb() {

        Map<String,Object> map = new HashMap<String, Object>();

        List<Bmb> list = bmbService.queryAllBmb();

        map.put("code", 200);
        map.put("message","查询成功！！！！");
        map.put("data",list);
        map.put("warring","计划经济哈哈哈哈哈哈哈");
        return map;
    }

    //添加成员

    @RequestMapping(value = "/addBmb1",method = RequestMethod.POST)
    @ResponseBody
    public Map addPeople1(Bmb bmb){
        System.out.println(bmb);
        Map<String,Object> map = new HashMap<String, Object>();
        int i = bmbService.saveBmb(bmb);
        System.out.println("=============="+i);
        if(i>0){
            map.put("code", 200);
            map.put("data",bmb);
            map.put("message","添加成功");
        }
        return map;
    }
}

