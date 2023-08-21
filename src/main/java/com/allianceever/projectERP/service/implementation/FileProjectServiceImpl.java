package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.FileProjectDto;
import com.allianceever.projectERP.model.entity.FileProject;
import com.allianceever.projectERP.repository.FileProjectRepo;
import com.allianceever.projectERP.service.FileProjectService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileProjectServiceImpl implements FileProjectService {
    private FileProjectRepo fileProjectRepo;
    private ModelMapper mapper;
    @Override
    public FileProjectDto create(FileProjectDto fileProjectDto) {
        // convert DTO to entity
        FileProject fileProject = mapToEntity(fileProjectDto);
        FileProject newFileProject = fileProjectRepo.save(fileProject);

        // convert entity to DTO
        return mapToDTO(newFileProject);
    }

    @Override
    public List<FileProjectDto> getAll() {
        List<FileProject> fileProjects = fileProjectRepo.findAll();
        return fileProjects.stream().map((fileProject) -> mapToDTO(fileProject))
                .collect(Collectors.toList());
    }

    @Override
    public List<FileProjectDto> findAll(String projectID) {
        List<FileProject> fileProjects = fileProjectRepo.findByProjectID(projectID);
        return fileProjects.stream().map((fileProject) -> mapToDTO(fileProject))
                .collect(Collectors.toList());
    }

    @Override
    public FileProjectDto getById(Long FileProjectID) {
        FileProject fileProject = fileProjectRepo.findById(FileProjectID).orElseThrow(
                () -> new ResourceNotFoundException("FileProject is not exist with given id : " + FileProjectID));

        return mapToDTO(fileProject);
    }

    @Override
    public void delete(Long FileProjectID) {
        FileProject fileProject = fileProjectRepo.findById(FileProjectID).orElseThrow(
                () -> new ResourceNotFoundException("FileProject is not exist with given id : " + FileProjectID));

        fileProjectRepo.deleteById(FileProjectID);
    }




    // convert entity into DTO
    private FileProjectDto mapToDTO(FileProject fileProject){
        FileProjectDto fileProjectDto = mapper.map(fileProject, FileProjectDto.class);
        return fileProjectDto;
    }

    // convert DTO to entity
    private FileProject mapToEntity(FileProjectDto fileProjectDto){
        FileProject fileProject = mapper.map(fileProjectDto, FileProject.class);
        return fileProject;
    }
}
