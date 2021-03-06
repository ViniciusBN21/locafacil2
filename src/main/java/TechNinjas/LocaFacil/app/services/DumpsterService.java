package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.models.Client;
import TechNinjas.LocaFacil.app.models.Dumpster;
import TechNinjas.LocaFacil.app.models.enums.Profile;
import TechNinjas.LocaFacil.app.repositories.DumpsterRepository;
import TechNinjas.LocaFacil.app.security.UserSS;
import TechNinjas.LocaFacil.app.services.exceptions.AuthorizationException;
import TechNinjas.LocaFacil.app.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DumpsterService {

    @Autowired
    private DumpsterRepository repository;

    private ModelMapper mapper = new ModelMapper();

    public Dumpster findById(Integer id) {
        UserSS userSS = UserService.authenticated();
        if((userSS == null || !userSS.hasRole(Profile.ADMIN)) && !id.equals(userSS.getId())) {
            throw new AuthorizationException("Acesso negado!");
        }

        Optional<Dumpster> obj = repository.findById(id);
        return obj.orElseThrow(() ->
                new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getSimpleName())
        );
    }

    public Dumpster create(Dumpster dump) {
        dump.setId(null);
        //dump.setCompany(dump.getCompany());
        return repository.save(dump);
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}





















