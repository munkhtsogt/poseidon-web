package edu.mum.se.poseidon.web.controllers;

import edu.mum.se.poseidon.web.mapper.SectionMapper;
import edu.mum.se.poseidon.web.models.SectionModel;
import edu.mum.se.poseidon.web.services.SectionService;
import edu.mum.se.poseidon.web.services.dto.SectionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SectionController {

    private SectionService sectionService;
    private SectionMapper sectionMapper;
    private static final Logger logger = LoggerFactory.getLogger(SectionController.class);

    @Autowired
    public SectionController(SectionService sectionService, SectionMapper sectionMapper) {
        this.sectionService = sectionService;
        this.sectionMapper = sectionMapper;
    }

    @RequestMapping(path = "/admin/section", method = RequestMethod.GET)
    public String index(Model model) throws Exception {
        List<SectionDto> sectionDtoList = sectionService.getSections();
        model.addAttribute("sections", sectionDtoList);
        return "admin/section/index";
    }

    @RequestMapping(path = "/admin/section/edit/{id}")
    public String edit(@PathVariable long id, Model model) throws Exception {
        SectionDto sectionDto = sectionService.getSection(id);
        SectionModel sectionModel = sectionMapper.getSectionModelFrom(sectionDto);
        model.addAttribute("sectionModel", sectionModel);
        return "admin/section/edit";
    }

    @RequestMapping(path = "/admin/section/edit/{id}", method = RequestMethod.POST)
    public String editPOST(@ModelAttribute("sectionModel") @Valid SectionModel sectionModel,
                           BindingResult bindingResult, @Valid Model model) throws Exception {
        System.out.println("AAAAA");
        if (bindingResult.hasErrors()) {
            return "admin/section/edit";
        }
        System.out.println("AAAAA1");
        sectionService.editSection(sectionMapper.getSectionDtoFrom(sectionModel));
        System.out.println("AAAAA2");
        return "redirect:/admin/section";
    }

    @RequestMapping(path = "/admin/section/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, Model model) throws Exception {
        sectionService.deleteSection(id);
        return "redirect:/admin/section";
    }
}