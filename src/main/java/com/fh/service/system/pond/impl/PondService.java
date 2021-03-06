package com.fh.service.system.pond.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Pond;
import com.fh.service.system.pond.PondManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("pondService")
public class PondService implements PondManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public int regPond(Pond pond) throws Exception {
        return (int)dao.save("PondMapper.regPond",pond);
    }

    @Override
    public int updatePondY(Pond pond) throws Exception {
        return (int)dao.update("PondMapper.updatePondY",pond);
    }

    @Override
    public int updatePondN(Pond pond) throws Exception {
        return (int)dao.update("PondMapper.updatePondN",pond);
    }

    @Override
    public Pond getPondByPlayId(Integer id) throws Exception {
        return (Pond) dao.findForObject("PondMapper.getPondByPlayId",id);
    }

    @Override
    public Pond getPondById(Integer id) throws Exception {
        return (Pond) dao.findForObject("PondMapper.getPondById",id);
    }
}
