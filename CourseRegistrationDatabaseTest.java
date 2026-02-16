package edu.jsu.mcis.cs310.coursedb;

import edu.jsu.mcis.cs310.coursedb.dao.*;
import org.junit.*;
import static org.junit.Assert.*;
import com.github.cliftonlabs.json_simple.*;

public class CourseRegistrationDatabaseTest {

    private static final String USERNAME = "nobody@jsu.edu";
    
    private DAOFactory daoFactory;
    private RegistrationDAO registrationDao;
    private SectionDAO sectionDao;
    private int studentid;
    
    private final String s1 = "[{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"20566\"}]";
    private final String s2 = "[{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"20540\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"20566\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"20966\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"21036\"}]";
    private final String s3 = "[{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"20540\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"20566\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"20726\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"20966\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"21036\"}]";
    private final String s4 = "[{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"20540\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"20566\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"20966\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"21036\"}]";
    private final String s5 = "[]";
    
    private final String s6 = "[{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"11:00:00\",\"days\":\"TR\",\"section\":\"001\",\"end\":\"12:30:00\",\"where\":\"Ayers Hall 363\",\"crn\":\"20531\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"James Mitchell Jensen\",\"num\":\"201\",\"start\":\"08:45:00\",\"days\":\"MWF\",\"section\":\"002\",\"end\":\"09:45:00\",\"where\":\"Ayers Hall 355\",\"crn\":\"20532\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"James Mitchell Jensen\",\"num\":\"201\",\"start\":\"12:30:00\",\"days\":\"MWF\",\"section\":\"004\",\"end\":\"13:30:00\",\"where\":\"Ayers Hall 355\",\"crn\":\"20533\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"09:15:00\",\"days\":\"TR\",\"section\":\"006\",\"end\":\"10:45:00\",\"where\":\"Ayers Hall 363\",\"crn\":\"20534\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"16:45:00\",\"days\":\"MW\",\"section\":\"012\",\"end\":\"18:15:00\",\"where\":\"Ayers Hall 357\",\"crn\":\"20536\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"12:45:00\",\"days\":\"TR\",\"section\":\"018\",\"end\":\"14:15:00\",\"where\":\"Ayers Hall 363\",\"crn\":\"20537\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Scarlet Jadzia Maddox\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"003\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20563\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Scarlet Jadzia Maddox\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"005\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20564\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Thomas D White\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"007\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20565\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"008\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20566\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Shreyashee Ghosh\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"009\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20567\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Shreyashee Ghosh\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"011\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20568\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Karen Bellman Lowe\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"013\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20569\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Thomas D White\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"014\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20570\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Scarlet Jadzia Maddox\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"015\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20571\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Shreyashee Ghosh\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"016\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20572\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Shreyashee Ghosh\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"017\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20573\",\"subjectid\":\"CS\"}]";
    private final String s7 = "[{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"David C Thornton\",\"num\":\"230\",\"start\":\"10:00:00\",\"days\":\"MWF\",\"section\":\"001\",\"end\":\"11:00:00\",\"where\":\"Ayers Hall 357\",\"crn\":\"20538\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Arup Kumar Ghosh\",\"num\":\"230\",\"start\":\"11:00:00\",\"days\":\"TR\",\"section\":\"002\",\"end\":\"12:30:00\",\"where\":\"Ayers Hall 359\",\"crn\":\"20539\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Keith Bradley Foster\",\"num\":\"230\",\"start\":\"12:30:00\",\"days\":\"MWF\",\"section\":\"003\",\"end\":\"13:30:00\",\"where\":\"Ayers Hall 357\",\"crn\":\"20540\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"James Mitchell Jensen\",\"num\":\"230\",\"start\":\"09:15:00\",\"days\":\"TR\",\"section\":\"004\",\"end\":\"10:45:00\",\"where\":\"Ayers Hall 357\",\"crn\":\"20541\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"James Mitchell Jensen\",\"num\":\"230\",\"start\":\"10:00:00\",\"days\":\"MWF\",\"section\":\"005\",\"end\":\"11:00:00\",\"where\":\"Ayers Hall 359\",\"crn\":\"20542\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"TBA\",\"num\":\"230\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"006\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"22416\",\"subjectid\":\"CS\"}]";
    private final String s8 = "[{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Christopher Ray Boles\",\"num\":\"112\",\"start\":\"10:00:00\",\"days\":\"MWF\",\"section\":\"002\",\"end\":\"11:00:00\",\"where\":\"Ayers Hall 118\",\"crn\":\"20612\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Christopher Ray Boles\",\"num\":\"112\",\"start\":\"11:15:00\",\"days\":\"MWF\",\"section\":\"003\",\"end\":\"12:15:00\",\"where\":\"Ayers Hall 118\",\"crn\":\"20613\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"BL2\",\"instructor\":\"Kelsey Anne Parrish\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"004\",\"end\":\"00:00:00\",\"where\":\"Ayers Hall 220\",\"crn\":\"20614\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Trenton Michael Townsend\",\"num\":\"112\",\"start\":\"16:30:00\",\"days\":\"MW\",\"section\":\"005\",\"end\":\"18:00:00\",\"where\":\"Ayers Hall 114\",\"crn\":\"20615\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"BL2\",\"instructor\":\"Gene Heaton Burchfield\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"006\",\"end\":\"00:00:00\",\"where\":\"Ayers Hall 359\",\"crn\":\"20616\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Clara Giulia Valtorta\",\"num\":\"112\",\"start\":\"09:15:00\",\"days\":\"TR\",\"section\":\"007\",\"end\":\"10:45:00\",\"where\":\"Ayers Hall 113\",\"crn\":\"20617\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Darius Williams\",\"num\":\"112\",\"start\":\"11:00:00\",\"days\":\"TR\",\"section\":\"008\",\"end\":\"12:30:00\",\"where\":\"Ayers Hall 253\",\"crn\":\"20618\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Darius Williams\",\"num\":\"112\",\"start\":\"12:45:00\",\"days\":\"TR\",\"section\":\"009\",\"end\":\"14:15:00\",\"where\":\"Ayers Hall 253\",\"crn\":\"20619\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Darius Williams\",\"num\":\"112\",\"start\":\"14:30:00\",\"days\":\"TR\",\"section\":\"010\",\"end\":\"16:00:00\",\"where\":\"Ayers Hall 253\",\"crn\":\"20620\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Trenton Michael Townsend\",\"num\":\"112\",\"start\":\"16:30:00\",\"days\":\"TR\",\"section\":\"011\",\"end\":\"18:00:00\",\"where\":\"Ayers Hall 113\",\"crn\":\"20621\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Sharon Padgett\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"012\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20671\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Sharon Padgett\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"013\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20672\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Amy E Carr\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"014\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20673\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Deedee S Henderson, Amy Paige Franklin\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"200\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"22282\",\"subjectid\":\"MS\"}]";
    private final String s9 = "[{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Jennifer L Gross\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"001\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20726\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Jennifer L Gross\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"002\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20727\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Richard Allan Dobbs\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"003\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20728\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Brandon Gilliland, Paul Richard Beezley\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"201\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"20741\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Sara Ann Herring, Cathy Glover Burrows\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"202\",\"end\":\"00:00:00\",\"where\":\"Southside High School\",\"crn\":\"20742\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cathy Glover Burrows\",\"num\":\"201\",\"start\":\"11:15:00\",\"days\":\"MWF\",\"section\":\"004\",\"end\":\"12:15:00\",\"where\":\"Stone Center 330\",\"crn\":\"21775\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"TBA\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"203\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"22270\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Barbara Ann Cook\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"005\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"22403\",\"subjectid\":\"HY\"}]";
    private final String s10 = "[]";
    
    @Before
    public void setUp() {
        
        daoFactory = new DAOFactory("coursedb");
        
        registrationDao = daoFactory.getRegistrationDAO();
        sectionDao = daoFactory.getSectionDAO();
        
        studentid = daoFactory.getStudentDAO().find(USERNAME);
        
    }
    
    @Test
    public void testRegisterSingle() {
        
        try {
        
            JsonArray r1 = (JsonArray)Jsoner.deserialize(s1);

            // clear schedule (withdraw all classes)

            registrationDao.delete(studentid, DAOUtility.TERMID_SP26);

            // register for one course

            boolean result = registrationDao.create(studentid, DAOUtility.TERMID_SP26, 20566);

            // compare number of updated records

            assertTrue(result);

            // compare schedule

            assertEquals(r1, (JsonArray)Jsoner.deserialize(registrationDao.list(studentid, DAOUtility.TERMID_SP26)));
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
    @Test
    public void testRegisterMultiple() {
        
        try {
        
            JsonArray r2 = (JsonArray)Jsoner.deserialize(s2);

            int[] crn = {20566, 20540, 20966, 21036};

            // clear schedule (withdraw all classes)

            registrationDao.delete(studentid, DAOUtility.TERMID_SP26);

            // register for multiple courses

            for (int i = 0; i < crn.length; ++i) {

                // add next course

                boolean result = registrationDao.create(studentid, DAOUtility.TERMID_SP26, crn[i]);

                // compare number of updated records

                assertTrue(result);

            }

            // compare schedule

            JsonArray t1 = (JsonArray)Jsoner.deserialize(registrationDao.list(studentid, DAOUtility.TERMID_SP26));
            assertEquals(4, t1.size());
            assertEquals(r2, t1);
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
    @Test
    public void testDropSingle() {
        
        try {
        
            JsonArray r3 = (JsonArray)Jsoner.deserialize(s3);

            int[] crn = {20566, 20721, 20540, 20966, 21036, 20726};

            // clear schedule (withdraw all classes)

            registrationDao.delete(studentid, DAOUtility.TERMID_SP26);

            // register for multiple courses

            for (int i = 0; i < crn.length; ++i) {

                // add next course

                boolean result = registrationDao.create(studentid, DAOUtility.TERMID_SP26, crn[i]);

                // compare number of updated records

                assertTrue(result);

            }
            
            // drop individual course
            
            boolean result = registrationDao.delete(studentid, DAOUtility.TERMID_SP26, 20721);
            
            assertTrue(result);

            // compare schedule

            JsonArray t1 = (JsonArray)Jsoner.deserialize(registrationDao.list(studentid, DAOUtility.TERMID_SP26));
            assertEquals(5, t1.size());
            assertEquals(r3, t1);
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
    @Test
    public void testDropMultiple() {
        
        try {
        
            JsonArray r4 = (JsonArray)Jsoner.deserialize(s4);

            int[] crn = {20566, 20721, 20540, 20966, 21036, 20726};

            // clear schedule (withdraw all classes)

            registrationDao.delete(studentid, DAOUtility.TERMID_SP26);

            // register for multiple courses

            for (int i = 0; i < crn.length; ++i) {

                // add next course

                boolean result = registrationDao.create(studentid, DAOUtility.TERMID_SP26, crn[i]);

                // compare number of updated records

                assertTrue(result);

            }
            
            // drop multiple courses
            
            boolean result = registrationDao.delete(studentid, DAOUtility.TERMID_SP26, 20721);
            
            assertTrue(result);
            
            result = registrationDao.delete(studentid, DAOUtility.TERMID_SP26, 20726);
            
            assertTrue(result);

            // compare schedule

            JsonArray t1 = (JsonArray)Jsoner.deserialize(registrationDao.list(studentid, DAOUtility.TERMID_SP26));
            assertEquals(4, t1.size());
            assertEquals(r4, t1);
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
    @Test
    public void testWithdraw() {
        
        try {
        
            JsonArray r5 = (JsonArray)Jsoner.deserialize(s5);

            int[] crn = {20566, 20721, 20540, 20966, 21036, 20726};

            // clear schedule (withdraw all classes)

            registrationDao.delete(studentid, DAOUtility.TERMID_SP26);

            // register for multiple courses

            for (int i = 0; i < crn.length; ++i) {

                // add next course

                boolean result = registrationDao.create(studentid, DAOUtility.TERMID_SP26, crn[i]);

                // compare number of updated records

                assertTrue(result);

            }

            // clear schedule (withdraw all classes)

            boolean result = registrationDao.delete(studentid, DAOUtility.TERMID_SP26);

            // check withdrawal

            assertTrue(result);

            // compare schedule

            JsonArray t1 = (JsonArray)Jsoner.deserialize(registrationDao.list(studentid, DAOUtility.TERMID_SP26));
            assertEquals(0, t1.size());
            assertEquals(r5, t1);
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
    @Test
    public void testGetSections() {
        
        try {
        
            JsonArray r6 = (JsonArray)Jsoner.deserialize(s6);
            JsonArray r7 = (JsonArray)Jsoner.deserialize(s7);
            JsonArray r8 = (JsonArray)Jsoner.deserialize(s8);
            JsonArray r9 = (JsonArray)Jsoner.deserialize(s9);
            JsonArray r10 = (JsonArray)Jsoner.deserialize(s10);

            // get sections; compare number of registered sections and JSON data

            // CS 201

            JsonArray t1 = (JsonArray)Jsoner.deserialize(sectionDao.find(1, "CS", "201"));
            assertEquals(17, t1.size());
            assertEquals(r6, t1);

            // CS 230

            JsonArray t2 = (JsonArray)Jsoner.deserialize(sectionDao.find(1, "CS", "230"));
            assertEquals(6, t2.size());
            assertEquals(r7, t2);

            // MS 112

            JsonArray t3 = (JsonArray)Jsoner.deserialize(sectionDao.find(1, "MS", "112"));
            assertEquals(14, t3.size());
            assertEquals(r8, t3);

            // HY 201

            JsonArray t4 = (JsonArray)Jsoner.deserialize(sectionDao.find(1, "HY", "201"));
            assertEquals(8, t4.size());
            assertEquals(r9, t4);

            // PHS 212 (should be empty)

            JsonArray t5 = (JsonArray)Jsoner.deserialize(sectionDao.find(1, "PHS", "212"));
            assertEquals(0, t5.size());
            assertEquals(r10, t5);
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
}