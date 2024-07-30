package com.example.bookingtour.service;

import com.example.bookingtour.dto.Admin_UserDTO;
import com.example.bookingtour.entity.RoleEntity;
import com.example.bookingtour.entity.UserEntity;
import com.example.bookingtour.exception.InsertUserException;
import com.example.bookingtour.exception.ObjectNotFoundException;
import com.example.bookingtour.payload.request.InsertUserRequest;
import com.example.bookingtour.repository.RoleRepository;
import com.example.bookingtour.repository.UserRepository;
import com.example.bookingtour.service.imp.FileServiceImp;
import com.example.bookingtour.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    private FileServiceImp fileServiceImp;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public boolean insertNewUser(InsertUserRequest request) {
        boolean isSucess = false;
        // getAvt la file
        boolean isSucessCopy = fileServiceImp.saveFile(request.getAvt());
        try{
            if(isSucessCopy) {
                UserEntity userEntity = new UserEntity();
                userEntity.setFullname(request.getFullname());
                userEntity.setEmail(request.getEmail());
                userEntity.setPassword(request.getPassword());
                userEntity.setAvt(request.getAvt().getOriginalFilename());
                userEntity.setPhone(request.getPhone());

                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setId(request.getIdRole());

                userEntity.setRole(roleEntity);

                userRepository.save(userEntity);
            }
            isSucess = true;
        } catch (Exception e){
            throw new InsertUserException();
        }
        return isSucess;
    }

    @Transactional
    @Override
    public boolean updateUserById(InsertUserRequest insertUserRequest, int id) {
        boolean isSucess = false;
        boolean isSucessCopy = fileServiceImp.saveFile(insertUserRequest.getAvt());
        if(!isSucessCopy){
            throw new InsertUserException("Lưu file ảnh không thành công!");
        }
        try{
            UserEntity userSaved = userRepository.findUserEntityById(id);
            if(userSaved != null){
                userSaved.setFullname(insertUserRequest.getFullname());
                userSaved.setEmail(insertUserRequest.getEmail()); // email khong duoc trung email nguoi khac
                userSaved.setPassword(insertUserRequest.getPassword());
                userSaved.setAvt(insertUserRequest.getAvt().getOriginalFilename());
                userSaved.setPhone(insertUserRequest.getPhone());
                RoleEntity roleSaved = roleRepository.findById(insertUserRequest.getIdRole());
                if(roleSaved != null){
                    roleSaved.setId(insertUserRequest.getIdRole());
                    userSaved.setRole(roleSaved);
                } else throw new InsertUserException("Không tìm thấy role!");
                userRepository.save(userSaved);
                isSucess = true;
            } else throw new InsertUserException("Không tìm thấy user với id = " + id);
        }catch(Exception e){
            throw new InsertUserException(e.getMessage());
        }
        return isSucess;
    }

    @Override
    public List<Admin_UserDTO> getAllForAdmin() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<Admin_UserDTO> adminUserDTOList = new ArrayList<>();

        if(adminUserDTOList.isEmpty()){
            throw new ObjectNotFoundException("Không tìm thấy bất kỳ thông tin User nào");
        }

        userEntityList.forEach(item -> {
            Admin_UserDTO adminUserDTO = new Admin_UserDTO();
            adminUserDTO.setIdUser(item.getId());
            adminUserDTO.setFullName(item.getFullname());
            adminUserDTO.setEmail(item.getEmail());
            adminUserDTO.setPhoneNumber(item.getPhone());
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setName(item.getRole().getName());
            adminUserDTO.setRoleUser(roleEntity);
            adminUserDTO.setAvatar("http://localhost:8080/file/" + item.getAvt());

            adminUserDTOList.add(adminUserDTO);
        });

        return adminUserDTOList;
    }

}
