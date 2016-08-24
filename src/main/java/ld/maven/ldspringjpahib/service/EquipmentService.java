package ld.maven.ldspringjpahib.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ld.maven.ldspringjpahib.dao.EquipmentRepository;
import ld.maven.ldspringjpahib.model.Equipment;
import ld.maven.ldspringjpahib.model.Team;

@Component
public class EquipmentService {
	private static final Logger LOG = LoggerFactory.getLogger(EquipmentService.class);
	
	@Autowired
	private EquipmentRepository jsonEquipmentRepository;
	
	// CREATE
	@Transactional
	public void add(Equipment jsonEquipment) {
		jsonEquipmentRepository.save(jsonEquipment);
	}

	@Transactional(readOnly=true)
	public Equipment findById(Integer id) {
		return jsonEquipmentRepository.findOne(id);
	}

	@Transactional(readOnly=true)
	public Equipment findOneByType(String type) {
		return jsonEquipmentRepository.findOneByType(type);
	}

	@Transactional(readOnly=true)
	public Equipment findOneByDetailsMainColor(String mainColor) {
		return jsonEquipmentRepository.findOneByDetailsMainColor(mainColor);
	}

	
}
