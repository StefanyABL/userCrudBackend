package com.cruduserApp.userApp.controller;

import com.cruduserApp.userApp.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.cruduserApp.userApp.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    List<Users> index(){
        return userRepository.findAll();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    Users create(@RequestBody Users users){
        Date fechaActual = new Date();
        users.setEdad(String.valueOf(calcularEdad(formatFecha(users.getFechaNacimiento()), fechaActual)));
        return userRepository.save(users);
    }
    @PutMapping("{id}")
    Users update (@PathVariable String id, @RequestBody Users users){
    Users usersUpdate = userRepository.findById(id).orElseThrow(RuntimeException::new);

        usersUpdate.setTipoIdentificacion(users.getTipoIdentificacion());
        usersUpdate.setCorreo(users.getCorreo());

        usersUpdate.setFechaNacimiento(users.getFechaNacimiento());
        Date fechaActual = new Date();
        usersUpdate.setEdad(users.getEdad());
        usersUpdate.setNumIdentificacion(users.getNumIdentificacion());
        usersUpdate.setTelefono(users.getTelefono());

        return userRepository.save(usersUpdate);
    }
    public static int calcularEdad(Date fechaNacimiento, Date fechaActual) {
        int edad = 0;

        // Calcular la diferencia de años
        long diferenciaEnMs = fechaActual.getTime() - fechaNacimiento.getTime();
        long diferenciaEnDias = diferenciaEnMs / (1000 * 60 * 60 * 24);
        edad = (int) (diferenciaEnDias / 365);

        return edad;
    }
    public static Date formatFecha(String fecha) {
        Date fechaNacimiento = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            fechaNacimiento = sdf.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fechaNacimiento;
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    void delete (@PathVariable String id){
        Users users = userRepository.findById(id).orElseThrow(RuntimeException::new);
        userRepository.delete(users);
    }
}
