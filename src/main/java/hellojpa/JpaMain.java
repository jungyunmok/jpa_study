package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        // persistence.xml에서 설정한 persistenceUnitName을 넘김
        // emf는 db당 딱 하나만 생성되야함 - 스레드간 공유하면 절대 안된다(사용하고 버려야함)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // em은 요청이 올때마다 생성
        EntityManager em = emf.createEntityManager();

        // 데이터의 모든 데이터 변경은 트랜젝션 안에서 실행해야한다!
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member findMenber = em.find(Member.class, 1L);

            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : result) {
                System.out.println(member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
