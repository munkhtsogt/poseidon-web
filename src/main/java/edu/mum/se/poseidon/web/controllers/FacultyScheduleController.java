package edu.mum.se.poseidon.web.controllers;

import edu.mum.se.poseidon.web.CustomAuthUser;
import edu.mum.se.poseidon.web.mapper.SectionMapper;
import edu.mum.se.poseidon.web.models.FacultyScheduleModel;
import edu.mum.se.poseidon.web.models.FacultySectionModel;
import edu.mum.se.poseidon.web.services.FacultyScheduleService;
import edu.mum.se.poseidon.web.services.dto.FacultyScheduleDto;
import edu.mum.se.poseidon.web.services.dto.FacultySectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FacultyScheduleController {

    private FacultyScheduleService facultyScheduleService;
    private SectionMapper sectionMapper;

    @Autowired
    public FacultyScheduleController(FacultyScheduleService facultyScheduleService,
                                     SectionMapper sectionMapper) {
        this.facultyScheduleService = facultyScheduleService;
        this.sectionMapper = sectionMapper;
    }

    @RequestMapping(path = "/faculty/schedule", method = RequestMethod.GET)
    public String getSchedule(Model model, Authentication authentication) throws Exception {
        try {
            CustomAuthUser user = (CustomAuthUser) authentication.getPrincipal();

            List<FacultyScheduleDto> facultyScheduleDtoList
                    = facultyScheduleService.getFacultySchedule(user.getId());

            List<FacultyScheduleModel> models = new ArrayList<>();
            for(FacultyScheduleDto dto: facultyScheduleDtoList) {
                FacultyScheduleModel mod = new FacultyScheduleModel();
                List<FacultySectionModel> sectionModelList = sectionMapper.getFacultySectionModelList(dto.getFacultySectionDtoList());
                mod.setEntryName(dto.getEntryName());
                mod.setSectionModelList(sectionModelList);

                models.add(mod);
            }
            // STUB!
//            List<FacultyScheduleModel> models = createStubScheduleModel();

            model.addAttribute("entries", models);
            return "faculty/schedule";
        } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    private List<FacultyScheduleModel> createStubScheduleModel() {
        List<FacultyScheduleModel> list = new ArrayList<>();

        List<FacultySectionModel> modelList = new ArrayList<>();
        FacultySectionModel sectionModel1 = new FacultySectionModel();
        sectionModel1.setId(1l);
        sectionModel1.setBlockName("September");
        sectionModel1.setStartDate("09/01/2017");
        sectionModel1.setEndDate("09/30/2017");
        sectionModel1.setLocation("Verill Hall 115");
        sectionModel1.setCourseFullName("Basketball 495");
        sectionModel1.setMaxSeats(25);

        FacultySectionModel sectionModel2 = new FacultySectionModel();
        sectionModel2.setId(2l);
        sectionModel2.setBlockName("October");
        sectionModel2.setStartDate("10/01/2017");
        sectionModel2.setEndDate("10/30/2017");
        sectionModel2.setLocation("Verill Hall 120");
        sectionModel2.setCourseFullName("Football 505");
        sectionModel2.setMaxSeats(25);

        FacultySectionModel sectionModel3 = new FacultySectionModel();
        sectionModel3.setId(3l);
        sectionModel3.setBlockName("November");
        sectionModel3.setStartDate("11/01/2017");
        sectionModel3.setEndDate("11/30/2017");
        sectionModel3.setLocation("Verill Hall 135");
        sectionModel3.setCourseFullName("Golf 530");
        sectionModel3.setMaxSeats(25);

        modelList.add(sectionModel1);
        modelList.add(sectionModel2);
        modelList.add(sectionModel3);

        FacultyScheduleModel model1 = new FacultyScheduleModel();
        model1.setEntryName("August 2017");
        model1.setSectionModelList(modelList);

        FacultyScheduleModel model2 = new FacultyScheduleModel();
        model2.setEntryName("October 2017");
        model2.setSectionModelList(modelList);

        FacultyScheduleModel model3 = new FacultyScheduleModel();
        model3.setEntryName("January 2018");
        model3.setSectionModelList(modelList);

        list.add(model1);
        list.add(model2);
        list.add(model3);

        return list;
    }
}
