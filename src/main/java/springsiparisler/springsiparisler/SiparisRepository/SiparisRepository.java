package springsiparisler.springsiparisler.SiparisRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import springsiparisler.springsiparisler.SiparisSchema.SiparisSchema;

@Repository
public interface SiparisRepository extends MongoRepository<SiparisSchema, String> {

}
