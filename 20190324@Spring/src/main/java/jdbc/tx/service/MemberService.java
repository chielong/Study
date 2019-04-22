package jdbc.tx.service;

import jdbc.tx.dao.MemberDao;
import jdbc.tx.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chielong on 2019-04-22.
 */
@Service
public class MemberService {
    @Autowired
    private MemberDao memberDao;

    public List<Member> queryAll() {
        try {
            return memberDao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(Member member) throws Exception {
        return memberDao.insert(member);
    }

    public boolean remove(long id) throws Exception {
        return memberDao.delete(id);
    }

    public boolean modify(long id , String name) throws Exception {
        return memberDao.update(id , name);
    }


}
