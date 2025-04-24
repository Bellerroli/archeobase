package hu.belrol.archeobase.meret;

import java.sql.SQLException;
import java.util.List;

public class MeretController {
    private static MeretController instance;

    private MeretController() {
    }

    public static MeretController getInstance() {
        if (instance == null) {
            instance = new MeretController();
        }
        return instance;
    }

    public Meret create(Meret meret) throws SQLException {
        return MeretDao.getInstance().insert(meret);
    }

    public List<Meret> getAll(int id) throws SQLException {
        return MeretDao.getInstance().findByStratId(id);
    }

    public List<String> findAttributes(String attribute) throws SQLException {
        return MeretDao.getInstance().findAttributeList(attribute);
    }

    public void deleteAll(int stratId) throws SQLException {
        MeretDao.getInstance().deleteByStratId(stratId);
    }
}
