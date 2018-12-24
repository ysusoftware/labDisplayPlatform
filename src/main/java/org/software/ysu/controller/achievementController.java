package org.software.ysu.controller;

import org.software.ysu.pojo.IntroductionExample;
import org.software.ysu.pojo.IntroductionWithBLOBs;
import org.software.ysu.pojo.Page;
import org.software.ysu.pojo.tableResponse;
import org.software.ysu.service.Interface.IIntroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erisu
 * @date 2018/12/22 16:10
 * @Description TODO
 * @Version 1.0
 **/
@RequestMapping("achievement")
@RestController
public class achievementController {
    @Resource
    IIntroService introService;
    @RequestMapping("getAchievements")
    public tableResponse getSubjects(Page page) {
        System.out.println("page="+page.toString());
         IntroductionExample introductionExample = new IntroductionExample();
        if (page.getContext() != ""){
            introductionExample.createCriteria().andIntroTitleEqualTo("%" + page.getContext() + "%");
        }
        List<IntroductionWithBLOBs>intros=introService.getIntros(introductionExample);
        int tempMin=Math.min(intros.size(),page.getPage() * page.getLimit()+1);
        //前台真正显示的数据
        List<IntroductionWithBLOBs>introPages=new ArrayList<>();
        for(int i=(page.getPage() - 1) * page.getLimit();i<tempMin;i++){
            introPages.add(intros.get(i));
        }
        tableResponse tableResponse=new tableResponse("0","",intros.size(),introPages);
        return tableResponse;
    }
}