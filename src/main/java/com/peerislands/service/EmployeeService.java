package com.peerislands.service;

import com.peerislands.dto.EmployeeDTO;
import com.peerislands.mapper.EmployeeMapper;
import com.peerislands.mapper.HobbyMapper;
import com.peerislands.model.Employee;
import com.peerislands.model.GenerateQueryHack;
import com.peerislands.model.Hobby;
import com.peerislands.repository.EmployeeRepository;
import com.peerislands.repository.ExtendedEmployeeRepository;
import com.peerislands.request.SearchQuery;
import com.peerislands.request.SearchQueryRequest;
import com.peerislands.utils.SpecificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ExtendedEmployeeRepository extendedEmployeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private HobbyMapper hobbyMapper;


    public String searchEmployeeQuery(SearchQueryRequest searchQueryRequest) {

        PageRequest pageRequest = SpecificationUtil.getPageRequest(searchQueryRequest);
        Specification<Employee> spec = SpecificationUtil.prepareSearchQuery(searchQueryRequest, Employee.class);
        String generatedSql = extendedEmployeeRepository.getGeneratedQuery(new GenerateQueryHack(spec, pageRequest));

        return generatedSql;
    }

    public List<Employee> searchEmployee(SearchQueryRequest searchQueryRequest) {
        PageRequest pageRequest = SpecificationUtil.getPageRequest(searchQueryRequest);
        Specification<Employee> spec = SpecificationUtil.prepareSearchQuery(searchQueryRequest, Employee.class);
        Page<Employee> page = employeeRepository.findAll(spec, pageRequest);
        return page.getContent();
    }

    @Transactional
    public void save(EmployeeDTO employeeDTO) {
        Employee entity = employeeMapper.toEntity(employeeDTO);
        List<Hobby> hobbies = hobbyMapper.toEntities(new ArrayList(employeeDTO.getHobbies()));
        for (Hobby hobby : hobbies) {
            hobby.setEmployee(entity);
        }
        Set<Hobby> hobbiesSet = hobbies.stream().collect(Collectors.toSet());
        entity.setHobbies(hobbiesSet);
        employeeRepository.save(entity);
    }

}