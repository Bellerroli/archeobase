package hu.belrol.archeobase.strat;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class StratController {
    private static StratController instance;
    private final StratDao stratDao;

    private StratController() {
        stratDao = StratDao.getInstance();
    }

    public static StratController getInstance() {
        if (instance == null) {
            instance = new StratController();
        }
        return instance;
    }

    public Strat insertPartialStrat(Strat strat) throws SQLException {
        return stratDao.insertPartialStrat(strat);
    }

    public Strat insertStrat(Strat strat) throws SQLException {
        return stratDao.insertStrat(strat);
    }

    public Strat updateStrat(Strat strat) throws SQLException {
        return stratDao.update(strat);
    }

    public List<Strat> findByAsatasId(int asatasId) throws SQLException {
        return stratDao.findByAsatasId(asatasId);
    }

    public List<String> findAllAttributes(String attribute) throws SQLException {
        return stratDao.findAttributeList(attribute);
    }

    public List<StratDto> findAllStratDtoByAsatasId(int asatasId) throws SQLException {
        List<Strat> stratList = findByAsatasId(asatasId);
        return stratList.stream()
                .map(strat -> {
                    try {
                        return StratMapper.getInstance().toDto(strat);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
