package com.audioapp.cms.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.util.StringUtils;
import com.audioapp.cms.dto.*;
import com.audioapp.cms.helper.ResultHelper;
import com.audioapp.cms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 平台数据
 * @author Administrator
 *
 */
@Controller
public class CmsDataController extends BaseController {
	
	@Autowired
	private DataService dataService;
	
	@Autowired
	private SubjectService subjectService;

	@Autowired
    private CourseService courseService;

	@Autowired
    private ReplyService replyService;

	/**
	 * 跳转总体数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goTotalData")
	public String goTotalData(HttpServletRequest request, Model model){
		TotalDataDTO totalData = new TotalDataDTO();
		//总用户数，总订阅金额
		Map<String, Object> totalMap = dataService.getTotalData();
		totalData.setTotalUsers(String.valueOf(totalMap.get("totalUsers")));
		totalData.setTotalSubAmt(String.valueOf(totalMap.get("totalSubAmt")));
		//昨日新增用户数，昨日活跃，昨日订阅金额
		Map<String, Object> yesMap = dataService.getYesData();
		totalData.setYesNewUsers(String.valueOf(yesMap.get("yesNewUsers")));
		totalData.setYesActUsers(String.valueOf(yesMap.get("yesActUsers")));
		totalData.setYesSubAmt(String.valueOf(yesMap.get("yesSubAmt")));
		//7日数据
		Map<String, Object> sevenMap = dataService.getSevenData();
		totalData.setSevenDayAct(String.valueOf(sevenMap.get("sevenActUsers")));
		//30日数据
		Map<String, Object> monthMap = dataService.getMonthData();
		totalData.setMonthAct(String.valueOf(monthMap.get("monthActUsers")));
		model.addAttribute("totalData", totalData);
        //查询列表数据
		String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
        Date startDate = null;
        if(!org.springframework.util.StringUtils.isEmpty(startDateStr)) {
            try {
                startDate = formatter.parse(startDateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Date endDate = null;
        if(!org.springframework.util.StringUtils.isEmpty(endDateStr)) {
            try {
                endDate = formatter.parse(endDateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startDate", startDate);
        paramMap.put("endData", endDate);
		TotalListDTO totalListData = dataService.getTotalList(paramMap);
		model.addAttribute("totalListData", totalListData);
		model.addAttribute("startDate", startDateStr);
        model.addAttribute("endDate", endDateStr);
		return "total_data";
	}
	
	/**
	 * 跳转专栏数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goSubjectData")
	public String goSubjectData(HttpServletRequest request, Model model){
        Map<String, Object> paramMap = new HashMap<>();
	    List<SubjectListDTO> subjectList = subjectService.getSubjectList(paramMap);
	    for(SubjectListDTO dto : subjectList){
			Map<String, Object> subMap = dataService.getSubjectCount(dto.getId());
			dto.setSubCnt(String.valueOf(subMap.get("orderCount")));
			dto.setSubAmt(String.valueOf(subMap.get("amtCount")));
			dto.setSubLenCnt(String.valueOf(dataService.getSubjectLearnCount(dto.getId())));
		}
        model.addAttribute("subjectList", subjectList);
		return "subject_data_list";
	}

	/**
	 * 跳转专栏数据详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goSubjectDataDetail")
	public String goSubjectDataDetail(HttpServletRequest request, Model model){
		Map<String, Object> paramMap = new HashMap<>();
        SubjectDataDetailDTO subjectData = new SubjectDataDetailDTO();
		Long subjectId = Long.valueOf(request.getParameter("subjectId"));
		paramMap.put("subjectId", subjectId);
		SubjectDetailDTO subjectDto = subjectService.getSubjectDetail(subjectId);
	    subjectData.setSubjectId(subjectId);
	    subjectData.setSubjectName(subjectDto.getSubjectName());
	    Map<String, Object> cntParamMap = new HashMap<>();
	    cntParamMap.put("subjectId", subjectId);
		Map<String, Object> cntMap = dataService.getSubjectOpenCnt(cntParamMap);
	    subjectData.setOpenCount(String.valueOf(cntMap.get("cnt")));
	    subjectData.setOpenPeopleCount(String.valueOf(cntMap.get("userCnt")));
        subjectData.setLearnCount(String.valueOf(dataService.getSubjectLearnCount(subjectId)));
        subjectData.setReplayCount(String.valueOf(replyService.getReplyCount(subjectId)));
        subjectData.setShareCount(String.valueOf(dataService.getShareCount(subjectId)));
        //查询专栏数据
        Map<String, Object> subjectMap = dataService.getSubjectCount(subjectId);
        subjectData.setSubscribeCount(String.valueOf(subjectMap.get("userCount")));
        subjectData.setSubscribeAmount(String.valueOf(subjectMap.get("amtCount")));
        //查询专栏下属的课程列表
        Map<String, Object> courseMap = new HashMap<>();
        courseMap.put("subjectId", subjectId);
        List<CourseListDTO> courseList = courseService.getCourseList(courseMap);
        for(CourseListDTO course : courseList){
        	course.setLearnCnt(String.valueOf(dataService.getCourseLearnCnt(course.getId())));
        	course.setShareCnt(String.valueOf(dataService.getCourseShareCnt(course.getId())));
        	course.setThumbsupCnt(String.valueOf(dataService.getCourseThumbsupCnt(course.getId())));
			course.setFreeCourseCnt(String.valueOf(dataService.getFreeCourseCnt(course.getId())));
		}
	    subjectData.setCourseList(courseList);
	    model.addAttribute("subjectData", subjectData);
		return "subject_data_detail";
	}
}
