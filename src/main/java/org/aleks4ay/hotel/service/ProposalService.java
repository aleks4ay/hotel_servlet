package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.ProposalDao;
import org.aleks4ay.hotel.model.Proposal;
import org.aleks4ay.hotel.model.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProposalService {
    private UserService userService = new UserService();

    public static void main(String[] args) {
        int y=1600;
        for (long i = 1000001; i<1000001+y; i++) {
            ProposalService service = new ProposalService();
            final Proposal order = service.getById(i);
            if (order != null) {
                System.out.print(order);
            }
        }
    }

    public Proposal getById(Long id) {
        Connection conn = ConnectionPool.getConnection();
        ProposalDao proposalDao = new ProposalDao(conn);
        Proposal proposal = proposalDao.getById(id);
        if (proposal != null) {
            proposal.setUser(userService.getById(proposal.getUser().getId()));
        }
        ConnectionPool.closeConnection(conn);
        return proposal;
    }

    public List<Proposal> getAll() {
        Connection conn = ConnectionPool.getConnection();
        ProposalDao proposalDao = new ProposalDao(conn);
        List<Proposal> proposals = proposalDao.findAll();
        Map<Long, User> users = new HashMap<>();

        for (User u : userService.getAll()) {
            users.put(u.getId(), u);
        }

        for (Proposal p : proposals) {
            p.setUser(users.get(p.getUser().getId()));
        }
        ConnectionPool.closeConnection(conn);
        return proposals;
    }

    public List<Proposal> getAllByUser(User user) {
        Connection conn = ConnectionPool.getConnection();
        ProposalDao proposalDao = new ProposalDao(conn);
        List<Proposal> orders = new ArrayList<>();

        for (Proposal o : proposalDao.findAll()) {
            if (o.getUser().getId() == user.getId()) {
                o.setUser(user);
                orders.add(o);
            }
        }
        ConnectionPool.closeConnection(conn);
        return orders;
    }

    public boolean delete(Long id) {
        Connection conn = ConnectionPool.getConnection();
        ProposalDao proposalDao = new ProposalDao(conn);
        boolean result = proposalDao.delete(id);
        ConnectionPool.closeConnection(conn);
        return result;
    }

    public Proposal create(Proposal proposal) {
        Connection conn = ConnectionPool.getConnection();
        ProposalDao proposalDao = new ProposalDao(conn);
        proposal.setStatus(Proposal.Status.NEW);
        proposal = proposalDao.create(proposal);
        ConnectionPool.closeConnection(conn);
        return proposal;
    }

    public List<Proposal> getAll(int positionOnPage, int page) {
        Connection conn = ConnectionPool.getConnection();
        ProposalDao proposalDao = new ProposalDao(conn);
        List<Proposal> proposals = proposalDao.findAll(positionOnPage, page);
        ConnectionPool.closeConnection(conn);
        return proposals;
    }

    public boolean updateStatus(Proposal.Status status, long id) {
        Connection conn = ConnectionPool.getConnection();
        ProposalDao proposalDao = new ProposalDao(conn);
        boolean result = proposalDao.updateStatus(status.toString(), id);
        ConnectionPool.closeConnection(conn);
        return result;
    }
}
