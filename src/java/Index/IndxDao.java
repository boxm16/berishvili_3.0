package Index;

import Service.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IndxDao {

    @Autowired
    private Converter converter;

}
