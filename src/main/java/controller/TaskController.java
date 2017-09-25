package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import po.Task;
import service.TaskService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UploadController uploadController;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insert(@RequestParam("publisherId") Integer publisherId, @RequestParam("title") String title,
                         @RequestParam("description") String description, @RequestParam("category") Integer category,
                         @RequestParam("price") Double price, @RequestParam("counts") Integer counts,
                         @RequestParam("starttime") Date starttime, @RequestParam("endtime") Date endtime) throws Exception {
        taskService.insertTask(publisherId, title, description, category, price, counts, starttime, endtime);
        return "ok";
    }

    @RequestMapping(value = "/insertWithPic", method = RequestMethod.POST)
    @ResponseBody
    public String insertWithPic(@RequestParam("publisherId") Integer publisherId, @RequestParam("title") String title,
                                @RequestParam("description") String description, @RequestParam("category") Integer category,
                                @RequestParam("price") Double price, @RequestParam("counts") Integer counts,
                                @RequestParam("starttime") Date starttime, @RequestParam("endtime") Date endtime,
                                @RequestParam("pic") MultipartFile pic) throws Exception {
        if (pic.isEmpty()) {
            taskService.insertTask(publisherId, title, description, category, price, counts, starttime, endtime);
        } else {
            String pic_url = uploadController.upload(pic);
            taskService.insertTaskWithPic(publisherId, title, description, category, price, counts, starttime, endtime, pic_url);
        }
        return "ok";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String edit(@RequestParam("taskId") Integer taskId,
                       @RequestParam("publisherId") Integer publisherId, @RequestParam("title") String title,
                       @RequestParam("description") String description, @RequestParam("category") Integer category,
                       @RequestParam("price") Double price, @RequestParam("counts") Integer counts,
                       @RequestParam("starttime") Date starttime, @RequestParam("endtime") Date endtime) {
        taskService.updateTask(taskId, publisherId, title, description, category, price, counts, starttime, endtime);
        return "ok";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String edit(@RequestParam("taskId") Integer taskId) {
        taskService.deleteTask(taskId);
        return "ok";
    }

    @RequestMapping(value = "/getAllTasksByPublisherId", method = RequestMethod.POST)
    @ResponseBody
    public List<Task> getAllTasksByPublisherId(@RequestParam("publisherId") Integer publisherId) {
        return taskService.selectTask(publisherId);
    }
}