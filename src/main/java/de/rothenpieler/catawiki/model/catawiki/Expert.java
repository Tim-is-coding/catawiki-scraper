package de.rothenpieler.catawiki.model.catawiki;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Represents an export on catawiki.com
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@Data
public class Expert {

    @Id
    private long id;
    private String name;

}
