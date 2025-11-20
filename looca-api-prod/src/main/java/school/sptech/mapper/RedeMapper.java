package school.sptech.mapper;

import school.sptech.dto.RedeDTO;
import school.sptech.entity.Rede;

public class RedeMapper {

    public static RedeDTO leituraToDTO(Rede rede) {
        return new RedeDTO(
                rede.getIdRede(),
                rede.getIdMaquina(),
                rede.getIdComponente(),
                rede.getDownload(),
                rede.getUpload(),
                rede.getPacketLoss(),
                rede.getDthCaptura()
        );
    }

    public static Rede leituraToEntity(RedeDTO dto) {
        Rede rede = new Rede();
        rede.setIdRede(dto.getIdRede());
        rede.setIdMaquina(dto.getIdMaquina());
        rede.setIdComponente(dto.getIdComponente());
        rede.setDownload(dto.getDownload());
        rede.setUpload(dto.getUpload());
        rede.setPacketLoss(dto.getPacketLoss());
        rede.setDthCaptura(dto.getDthCaptura());
        return rede;
    }
}
