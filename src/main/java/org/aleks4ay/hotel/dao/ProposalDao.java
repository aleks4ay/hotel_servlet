package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.ProposalMapper;
import org.aleks4ay.hotel.model.Proposal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class ProposalDao extends AbstractDao<Long, Proposal>{
    private static final Logger log = LogManager.getLogger(ProposalDao.class);
    private static final String SQL_GET_ONE = "SELECT * FROM proposal WHERE id = ?;";
    private static final String SQL_GET_ALL = "SELECT * FROM proposal;";
    private static final String SQL_DELETE = "DELETE FROM proposal WHERE id = ?;";
    private static final String SQL_CREATE = "INSERT INTO proposal (registered, arrival, departure, guests," +
            " category, status, user_id) VALUES (?, ?, ?, ?, ?, ?, ?); ";
    private static final String SQL_UPDATE_STATUS = "UPDATE proposal SET status=? WHERE id=?;";

    public ProposalDao(Connection connection) {
        super(connection, new ProposalMapper());
    }

    @Override
    public Proposal getById(Long id) {
        return getAbstractById(SQL_GET_ONE, id);
    }

    @Override
    public List<Proposal> findAll() {
        return findAbstractAll(SQL_GET_ALL);
    }

    @Override
    public boolean delete(Long id) {
        return deleteAbstract(SQL_DELETE, id);
    }

    @Override
    public Proposal update(Proposal proposal) {
        return null;
    }

    @Override
    public Proposal create(Proposal proposal) {
        return createAbstract(proposal, SQL_CREATE);
    }

    public boolean updateStatus(String s, long id) {
        return updateStringAbstract(s, id, SQL_UPDATE_STATUS);
    }

}
