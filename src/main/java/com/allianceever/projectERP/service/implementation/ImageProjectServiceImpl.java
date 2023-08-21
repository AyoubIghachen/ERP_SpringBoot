package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.ImageProjectDto;
import com.allianceever.projectERP.model.entity.ImageProject;
import com.allianceever.projectERP.repository.ImageProjectRepo;
import com.allianceever.projectERP.service.ImageProjectService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageProjectServiceImpl implements ImageProjectService {
    private ImageProjectRepo imageProjectRepo;
    private ModelMapper mapper;
    @Override
    public ImageProjectDto create(ImageProjectDto imageProjectDto) {
        // convert DTO to entity
        ImageProject imageProject = mapToEntity(imageProjectDto);
        ImageProject newImageProject = imageProjectRepo.save(imageProject);

        // convert entity to DTO
        return mapToDTO(newImageProject);
    }

    @Override
    public List<ImageProjectDto> getAll() {
        List<ImageProject> imageProjects = imageProjectRepo.findAll();
        return imageProjects.stream().map((imageProject) -> mapToDTO(imageProject))
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageProjectDto> findAll(String projectID) {
        List<ImageProject> imageProjects = imageProjectRepo.findByProjectID(projectID);
        return imageProjects.stream().map((imageProject) -> mapToDTO(imageProject))
                .collect(Collectors.toList());
    }

    @Override
    public ImageProjectDto getById(Long ImageProjectID) {
        ImageProject imageProject = imageProjectRepo.findById(ImageProjectID).orElseThrow(
                () -> new ResourceNotFoundException("ImageProject is not exist with given id : " + ImageProjectID));

        return mapToDTO(imageProject);
    }

    @Override
    public void delete(Long ImageProjectID) {
        ImageProject imageProject = imageProjectRepo.findById(ImageProjectID).orElseThrow(
                () -> new ResourceNotFoundException("ImageProject is not exist with given id : " + ImageProjectID));

        imageProjectRepo.deleteById(ImageProjectID);
    }




    // convert entity into DTO
    private ImageProjectDto mapToDTO(ImageProject imageProject){
        ImageProjectDto imageProjectDto = mapper.map(imageProject, ImageProjectDto.class);
        return imageProjectDto;
    }

    // convert DTO to entity
    private ImageProject mapToEntity(ImageProjectDto imageProjectDto){
        ImageProject imageProject = mapper.map(imageProjectDto, ImageProject.class);
        return imageProject;
    }
}
