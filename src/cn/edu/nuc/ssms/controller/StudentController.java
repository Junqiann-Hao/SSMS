package cn.edu.nuc.ssms.controller;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.Notice;
import cn.edu.nuc.ssms.entity.po.Student;
import cn.edu.nuc.ssms.entity.po.Subject;
import cn.edu.nuc.ssms.entity.vo.AnalyseVo;
import cn.edu.nuc.ssms.service.StudentService;
import cn.edu.nuc.ssms.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 王启良
 * @Date 2017/4/28 22:07
 * @Description : 学生角色控制器
 */
@Controller
@RequestMapping("/Student")
public class StudentController {
    private static final Log log = LogFactory.getLog(StudentController.class);
    @Autowired
    StudentService studentService;

    /**
     * 查询所有公告
     * @return
     */
    @RequestMapping("/selectAllNotice")
    @ResponseBody
    public List<Notice> selectAllNotice() {
        log.debug("查询所有公告");
        List<Notice> notices = studentService.selectAllNodice();
        return notices;
    }

    /**
     * 分页查询公告
     * @param map
     * @return
     */
    @RequestMapping("/selectNotice")
    @ResponseBody
    public List<Notice> selectNotice(@RequestBody Map<String,Integer> map) {
        log.debug("分页查询公告");
        int pageSize = 20;
        if (map.containsKey("pageSize")) {
            pageSize = map.get("pageSize");
        }
        List<Notice> notices = studentService.selectNodice(map.get("startRow"), pageSize);
        return notices;
    }

    /**
     * 查询当前登陆的学生
     * @param request
     * @return
     */
    @RequestMapping("/selectStudent")
    @ResponseBody
    public Map selectStudent(HttpServletRequest request) {
        log.debug("查询当前登陆的学生");
        HashMap resultMap = new HashMap();
        resultMap.put("code", 1);
        int userIdCookie = Utils.getUserIdCookie(request.getCookies());
        if (userIdCookie != 0) {
            Student student = studentService.selectStudent(userIdCookie);
            resultMap.put("code", 0);
            resultMap.put("student", student);
        }
        return resultMap;
    }

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    @RequestMapping("/updateStudent")
    @ResponseBody
    public String updateStudent(@RequestBody Student student) {
        log.debug("修改学生信息");
        if (studentService.updateStuden(student)) {
            return "{\"code\": \"0\"}";
        }
        return "{\"code\": \"1\"}";
    }

    /**
     * 查询全班成绩
     * @param studentCustom
     * @return
     */
    @RequestMapping("/selectStudentCustomByClass")
    @ResponseBody
    public List<StudentCustom> selectStudentCustomByClass(@RequestBody StudentCustom studentCustom) {
        log.debug("查询全班成绩");
        List<StudentCustom> studentCustoms = studentService.selectStudentCustomByClass(studentCustom);
        return studentCustoms;
    }

    /**
     * 查询全班学生信息
     * @param student
     * @return
     */
    @RequestMapping("/testSelectStudentByClass")
    @ResponseBody
    public List<Student> testSelectStudentByClass(@RequestBody Student student) {
        log.debug("查询全班学生信息");
        List<Student> students = studentService.selectStudentByClass(student);
        return students;
    }

    /**
     * 查询教师所授课程
     * @param subject
     * @return
     */
    @RequestMapping("/selectSubjectByTeacher")
    @ResponseBody
    public List<Subject> selectSubjectByTeacher(@RequestBody Subject subject) {
        log.debug("查询教师所授课程");
        List<Subject> subjects = studentService.selectSubjectByTeacher(subject);
        return subjects;
    }

    /**
     * 班级成绩分析
     * @param analyseVo
     * @return
     */
    @RequestMapping("/analyseByClass")
    @ResponseBody
    public List<Map<String,Object>> analyseByClass(@RequestBody AnalyseVo analyseVo) {
        log.debug("班级成绩分析");

        List<Map<String,Object>> stringMapMap = studentService.analyseByClass(analyseVo);

        return stringMapMap;
    }

    /**
     * 学院成绩分析
     * @param analyseVo
     * @return
     */
    @RequestMapping("/analyseByCollage")
    @ResponseBody
    public List<Map<String,Object>> analyseByCollage(@RequestBody AnalyseVo analyseVo) {
        log.debug("学院成绩分析");

        List<Map<String,Object>> stringMapMap = studentService.analyseByCollage(analyseVo);

        return stringMapMap;
    }
}
