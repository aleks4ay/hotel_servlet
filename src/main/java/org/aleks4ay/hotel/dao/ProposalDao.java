package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.ProposalMapper;
import org.aleks4ay.hotel.model.Proposal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ProposalDao extends AbstractDao<Long, Proposal>{
    private static final Logger log = LogManager.getLogger(ProposalDao.class);

    public ProposalDao(Connection connection) {
        super(connection, new ProposalMapper());
    }

    @Override
    public Optional<Proposal> findById(Long id) {
        return getAbstractById("SELECT * FROM proposal WHERE id = ?;", id);
    }

    @Override
    public List<Proposal> findAll() {
        return findAbstractAll("SELECT * FROM proposal;");
    }

/*    @Override
    public boolean delete(Long id) {
        return deleteAbstract("DELETE FROM proposal WHERE id = ?;", id);
    }*/

    @Override
    public Optional<Proposal> create(Proposal proposal) {
        String sql = "INSERT INTO proposal (registered, arrival, departure, guests," +
                " category, status, user_id) VALUES (?, ?, ?, ?, ?, ?, ?); ";
        return createAbstract(proposal, sql);
    }

    public boolean updateStatus(String s, long id) {
        return updateStringAbstract(s, id, "UPDATE proposal SET status=? WHERE id=?;");
    }

}
