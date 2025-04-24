package hu.belrol.archeobase.asatas;

import java.sql.SQLException;
import java.util.List;

public class AsatasController {
    private static AsatasController instance;
    private final AsatasDao asatasDao;

    private AsatasController() {
        asatasDao = AsatasDao.getInstance();
    }

    public static AsatasController getInstance() {
        if (instance == null) {
            instance = new AsatasController();
        }
        return instance;
    }

    public Asatas insert(final Asatas asatas) throws SQLException {
        return asatasDao.create(asatas);
    }

    public List<Asatas> getAll() throws SQLException {
        return asatasDao.getAll();
    }
}
