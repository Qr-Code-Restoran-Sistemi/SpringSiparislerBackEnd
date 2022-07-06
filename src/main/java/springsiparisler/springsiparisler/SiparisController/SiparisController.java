package springsiparisler.springsiparisler.SiparisController;

import java.beans.JavaBean;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springsiparisler.springsiparisler.SiparisRepository.SiparisRepository;
import springsiparisler.springsiparisler.SiparisSchema.SiparisSchema;
import springsiparisler.springsiparisler.SiparisSchema.UrunSchema;

@JavaBean
@CrossOrigin(origins = "http://localhost:8085/")
@RequestMapping
@RestController
public class SiparisController {

    @Autowired

    SiparisRepository siparisRepository;
    HttpStatus httpStatus;

    @GetMapping("/Listele")
    public List<SiparisSchema> Listele() {
        return siparisRepository.findAll();
    }

    @PostMapping("/kisiekle")
    public ResponseEntity<SiparisSchema> SiparisKaydet(
            @RequestBody(required = true) @Valid SiparisSchema siparisSchema,
            UrunSchema urunSchema) {
        try {
            SiparisSchema _sSchema = siparisRepository.save(
                    new SiparisSchema(siparisSchema.getMasaNo(),
                            siparisSchema.getUrun(urunSchema.getUrun(), urunSchema.getFiyat(),
                                    urunSchema.getIcindekiler()),
                            siparisSchema.getEkstralar(),
                            siparisSchema.getNot(), siparisSchema.getToplamFiyat(), siparisSchema.isSiparisAktifMi()));
            return new ResponseEntity<>(_sSchema, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/silme")
    public String silme(@RequestBody(required = true) SiparisSchema siparisSchema) {
        siparisRepository.deleteById(siparisSchema.getId());
        return "Başarı ile silindi";
    }

    @PutMapping("düzenleme")
    public ResponseEntity<SiparisSchema> düzenleme(@RequestBody(required = true) SiparisSchema siparisSchema,
            UrunSchema urunSchema) {
        siparisRepository.findById(siparisSchema.getId());
        siparisSchema.setMasaNo(siparisSchema.getMasaNo());
        siparisSchema.setUrun(
                siparisSchema.getUrun(urunSchema.getUrun(), urunSchema.getFiyat(), urunSchema.getIcindekiler()));
        siparisSchema.setEkstralar(siparisSchema.getEkstralar());
        siparisSchema.setNot(siparisSchema.getNot());
        siparisSchema.setSiparisAktifMi(siparisSchema.isSiparisAktifMi());
        final SiparisSchema düzenlenmisSiparis = siparisRepository.save(siparisSchema);
        return ResponseEntity.ok(düzenlenmisSiparis);

    }
}
