package collage;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class CollageApplicationTest extends ApplicationTest {

    private Stage stage;

    static {
        if (Boolean.getBoolean("SERVER")) {
            System.setProperty("java.awt.headless", "true");
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("glass.platform", "Monocle");
            System.setProperty("monocle.platform", "Headless");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        CollageApplication application = new CollageApplication();

        try {
            Application app = Application.class.cast(application);
        } catch (Throwable t) {
            fail("Make sure the CollageApplication class inherits the Application class.");
        }

        try {
            Reflex.reflect(CollageApplication.class).method("start").returningVoid().taking(Stage.class).invokeOn(application, stage);
        } catch (Throwable ex) {
            fail("Make sure that the CollageApplication class has a method called start that takes a Stage object as its parameter. If this is the case, make sure that the method works correctly. The error: " + ex.getMessage());
        }

        this.stage = stage;
    }

    @Test
    public void stageContainsPaneThatContainsImageView() {
        getImageView();
    }

    @Test
    @Points("14-07.1")
    public void part1() {
        ImageView view = getImageView();
        Image image = view.getImage();

        PixelReader pixelReader = image.getPixelReader();

        for (String positionAndColor : part1PixelsExpectedValues()) {
            String[] arr = positionAndColor.split("\t");
            Color c = pixelReader.getColor(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));

            if (arr[2].equals(c.toString()) || arr[3].equals(c.toString())) {
                continue;
            }

            fail("Copying to the corner did not work as expected. At position " + arr[0] + ", " + arr[1] + " the value was unexpected.");
        }
    }

    @Test
    @Points("14-07.2")
    public void part2() {
        ImageView view = getImageView();
        Image image = view.getImage();

        PixelReader pixelReader = image.getPixelReader();

        for (String positionAndColor : part2PixelsExpectedValues()) {
            String[] arr = positionAndColor.split("\t");
            Color c = pixelReader.getColor(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));

            if (arr[2].equals(c.toString()) || arr[3].equals(c.toString())) {
                continue;
            }

            fail("Copying the small image to all four corners did not work as expected. At the position " + arr[0] + ", " + arr[1] + " the value was unexpected.");
        }
    }

    @Test
    @Points("14-07.3")
    public void part3() {
        ImageView view = getImageView();
        Image image = view.getImage();

        PixelReader pixelReader = image.getPixelReader();

        List<String> values = new ArrayList<>();
        values.addAll(part1PixelsExpectedValues());
        values.addAll(part2PixelsExpectedValues());

        for (String positionAndColor : values) {
            String[] arr = positionAndColor.split("\t");
            Color c = pixelReader.getColor(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));

            if (arr[3].equals(c.toString())) {
                continue;
            }

            fail("Creating the negative did not work as expected. At the position " + arr[0] + ", " + arr[1] + " the value was unexpected.");
        }
    }

    private ImageView getImageView() {
        Scene scene = stage.getScene();
        assertNotNull("The Stage object should contain a Scene object. Now after calling getScene after the start method has executed returned the null reference.", scene);
        Parent rootElement = scene.getRoot();
        assertNotNull("The Scene object should receive as its parameter a component that is responsible for the layout (in this case Pane). Now the Scene object contained no such components.", rootElement);

        Pane layout = null;
        try {
            layout = Pane.class.cast(rootElement);
        } catch (Throwable t) {
            fail("Make sure you are using the Pane class for the layout of the first view.");
        }

        assertNotNull("The Scene object should receive as its parameter a Pane.", layout);
        assertFalse("The Pane object should have one child, which is of type ImageView. Now there were no children.", layout.getChildren().isEmpty());
        assertTrue("The Pane object should have one child, which is of type ImageView. Now the number of children was ", layout.getChildren().size() == 1);

        assertTrue("The Pane object should have one child, which is of type ImageView.", layout.getChildren().get(0).getClass().isAssignableFrom(ImageView.class));

        return (ImageView) layout.getChildren().get(0);
    }

    // TODO: more tests
    
    private List<String> part1PixelsExpectedValues() {
        return Arrays.asList(("0	249	0x392e2aff	0xc6d1d5ff\n"
                + "0	287	0x30271eff	0xcfd8e1ff\n"
                + "0	326	0x251f15ff	0xdae0eaff\n"
                + "0	334	0x272019ff	0xd8dfe6ff\n"
                + "5	29	0x69654bff	0x969ab4ff\n"
                + "8	283	0x372318ff	0xc8dce7ff\n"
                + "9	38	0x777657ff	0x8889a8ff\n"
                + "10	144	0x403930ff	0xbfc6cfff\n"
                + "14	8	0x544e41ff	0xabb1beff\n"
                + "15	293	0x392f2aff	0xc6d0d5ff\n"
                + "18	187	0x553b21ff	0xaac4deff\n"
                + "18	336	0x211611ff	0xdee9eeff\n"
                + "19	8	0x4d4a37ff	0xb2b5c8ff\n"
                + "19	177	0x604121ff	0x9fbedeff\n"
                + "27	195	0x55361cff	0xaac9e3ff\n"
                + "32	354	0x291c13ff	0xd6e3ecff\n"
                + "34	58	0x8c8868ff	0x737797ff\n"
                + "34	150	0x3e382aff	0xc1c7d5ff\n"
                + "34	331	0x271a11ff	0xd8e5eeff\n"
                + "35	75	0x9f9674ff	0x60698bff\n"
                + "40	292	0x3c2417ff	0xc3dbe8ff\n"
                + "46	160	0x473517ff	0xb8cae8ff\n"
                + "49	78	0x817e55ff	0x7e81aaff\n"
                + "49	268	0x3a2a1dff	0xc5d5e2ff\n"
                + "50	329	0x2e211dff	0xd1dee2ff\n"
                + "54	236	0x2c231cff	0xd3dce3ff\n"
                + "57	53	0x8a8566ff	0x757a99ff\n"
                + "57	282	0x8a6031ff	0x759fceff\n"
                + "62	210	0x312d2aff	0xced2d5ff\n"
                + "63	317	0x241b11ff	0xdbe4eeff\n"
                + "69	290	0x5e3d20ff	0xa1c2dfff\n"
                + "74	106	0x44422fff	0xbbbdd0ff\n"
                + "75	164	0x41371eff	0xbec8e1ff\n"
                + "78	84	0x988a5aff	0x6775a5ff\n"
                + "79	171	0x3b2d1cff	0xc4d2e3ff\n"
                + "85	225	0x281f17ff	0xd7e0e8ff\n"
                + "85	240	0x272115ff	0xd8deeaff\n"
                + "87	174	0x25201dff	0xdadfe2ff\n"
                + "88	267	0x221a16ff	0xdde5e9ff\n"
                + "90	362	0x241b12ff	0xdbe4edff\n"
                + "93	343	0x3c2616ff	0xc3d9e9ff\n"
                + "99	11	0x5c5d4fff	0xa3a2b0ff\n"
                + "100	9	0x5c5d50ff	0xa3a2afff\n"
                + "103	118	0x8d6035ff	0x729fcaff\n"
                + "103	226	0x271f12ff	0xd8e0edff\n"
                + "104	364	0x24170fff	0xdbe8f0ff\n"
                + "106	156	0x27201aff	0xd8dfe5ff\n"
                + "106	269	0x241e10ff	0xdbe1efff\n"
                + "107	34	0x787257ff	0x878da8ff\n"
                + "110	99	0xbb8956ff	0x4476a9ff\n"
                + "111	113	0xaf7d4aff	0x5082b5ff\n"
                + "111	263	0x2b2516ff	0xd4dae9ff\n"
                + "112	84	0xc08440ff	0x3f7bbfff\n"
                + "112	243	0x2f2618ff	0xd0d9e7ff\n"
                + "116	139	0x342a27ff	0xcbd5d8ff\n"
                + "116	278	0x1b1610ff	0xe4e9efff\n"
                + "119	354	0x2d1f10ff	0xd2e0efff\n"
                + "125	9	0x4d5247ff	0xb2adb8ff\n"
                + "129	42	0x3a2e22ff	0xc5d1ddff\n"
                + "129	371	0x1d140dff	0xe2ebf2ff\n"
                + "131	152	0x664627ff	0x99b9d8ff\n"
                + "132	299	0x1a150fff	0xe5eaf0ff\n"
                + "132	300	0x1a1411ff	0xe5ebeeff\n"
                + "133	230	0x25211bff	0xdadee4ff\n"
                + "134	36	0x5a503fff	0xa5afc0ff\n"
                + "136	122	0x784e27ff	0x87b1d8ff\n"
                + "150	59	0x362e26ff	0xc9d1d9ff\n"
                + "151	150	0x53351aff	0xaccae5ff\n"
                + "157	135	0x382f2aff	0xc7d0d5ff\n"
                + "158	1	0x3d3a29ff	0xc2c5d6ff\n"
                + "158	316	0x50290eff	0xafd6f1ff\n"
                + "159	244	0x1b1513ff	0xe4eaecff\n"
                + "161	274	0x1c150fff	0xe3eaf0ff\n"
                + "163	7	0x5d5e50ff	0xa2a1afff\n"
                + "164	237	0x201a16ff	0xdfe5e9ff\n"
                + "167	193	0x8c6438ff	0x739bc7ff\n"
                + "168	11	0x68664fff	0x9799b0ff\n"
                + "168	95	0x342f28ff	0xcbd0d7ff\n"
                + "169	298	0x1a1511ff	0xe5eaeeff\n"
                + "169	350	0x1f1410ff	0xe0ebefff\n"
                + "172	351	0x1e1712ff	0xe1e8edff\n"
                + "178	69	0x717157ff	0x8e8ea8ff\n"
                + "178	79	0x5e5d46ff	0xa1a2b9ff\n"
                + "178	333	0x29180cff	0xd6e7f3ff\n"
                + "185	8	0x5c5c4eff	0xa3a3b1ff\n"
                + "185	15	0x656453ff	0x9a9bacff\n"
                + "185	82	0x55543fff	0xaaabc0ff\n"
                + "185	195	0x251c13ff	0xdae3ecff\n"
                + "192	251	0x23160dff	0xdce9f2ff\n"
                + "194	150	0x504831ff	0xafb7ceff\n"
                + "198	171	0x2b1f11ff	0xd4e0eeff\n"
                + "205	253	0x20150fff	0xdfeaf0ff\n"
                + "213	296	0x1d110dff	0xe2eef2ff\n"
                + "214	148	0x413d32ff	0xbec2cdff\n"
                + "214	360	0x17120dff	0xe8edf2ff\n"
                + "215	26	0x717262ff	0x8e8d9dff\n"
                + "218	69	0x837f59ff	0x7c80a6ff\n"
                + "218	132	0x3f403aff	0xc0bfc5ff\n"
                + "219	120	0x404236ff	0xbfbdc9ff\n"
                + "219	273	0x25190dff	0xdae6f2ff\n"
                + "224	47	0x7d7851ff	0x8287aeff\n"
                + "225	174	0x5f4722ff	0xa0b8ddff\n"
                + "226	101	0x7c744bff	0x838bb4ff\n"
                + "227	107	0x50503cff	0xafafc3ff\n"
                + "233	280	0x221811ff	0xdde7eeff\n"
                + "233	362	0x21120bff	0xdeedf4ff\n"
                + "234	251	0x241b12ff	0xdbe4edff\n"
                + "236	19	0x656550ff	0x9a9aafff\n"
                + "236	335	0x23160eff	0xdce9f1ff\n"
                + "237	137	0x3b392cff	0xc4c6d3ff\n"
                + "238	174	0x684f27ff	0x97b0d8ff\n"
                + "250	26	0x707056ff	0x8f8fa9ff\n"
                + "250	311	0x24170cff	0xdbe8f3ff\n"
                + "251	27	0x706e59ff	0x8f91a6ff\n"
                + "253	58	0x827e5aff	0x7d81a5ff\n"
                + "256	61	0x7e775bff	0x8188a4ff").split("\n"));
    }

    private List<String> part2PixelsExpectedValues() {
        return Arrays.asList(("2	199	0x281f17ff	0xd7e0e8ff\n"
                + "2	501	0x28241aff	0xd7dbe5ff\n"
                + "3	17	0x555644ff	0xaaa9bbff\n"
                + "3	736	0x1f1815ff	0xe0e7eaff\n"
                + "8	349	0x2a2018ff	0xd5dfe7ff\n"
                + "8	726	0x201715ff	0xdfe8eaff\n"
                + "9	76	0x716749ff	0x8e98b6ff\n"
                + "9	533	0x3e362bff	0xc1c9d4ff\n"
                + "15	690	0x2b1e18ff	0xd4e1e7ff\n"
                + "16	261	0x402d1fff	0xbfd2e0ff\n"
                + "16	348	0x2b1d18ff	0xd4e2e7ff\n"
                + "17	322	0x211610ff	0xdee9efff\n"
                + "23	371	0x1c130dff	0xe3ecf2ff\n"
                + "23	593	0x653b24ff	0x9ac4dbff\n"
                + "23	697	0x251a16ff	0xdae5e9ff\n"
                + "27	170	0x58472fff	0xa7b8d0ff\n"
                + "27	594	0x83512fff	0x7caed0ff\n"
                + "32	712	0x281b13ff	0xd7e4ecff\n"
                + "37	28	0x76745cff	0x898ba3ff\n"
                + "37	428	0x847d5dff	0x7b82a2ff\n"
                + "38	417	0x83826eff	0x7c7d91ff\n"
                + "40	79	0x90815aff	0x6f7ea5ff\n"
                + "43	74	0xa89e70ff	0x57618fff\n"
                + "45	167	0x513c21ff	0xaec3deff\n"
                + "45	549	0x4a3925ff	0xb5c6daff\n"
                + "48	132	0x3f3825ff	0xc0c7daff\n"
                + "49	306	0x291d13ff	0xd6e2ecff\n"
                + "49	632	0x362a23ff	0xc9d5dcff\n"
                + "52	196	0x533618ff	0xacc9e7ff\n"
                + "54	22	0x63614eff	0x9c9eb1ff\n"
                + "54	679	0x3d2719ff	0xc2d8e6ff\n"
                + "55	72	0x9e9979ff	0x616686ff\n"
                + "56	97	0x4b4934ff	0xb4b6cbff\n"
                + "58	112	0x413f2fff	0xbec0d0ff\n"
                + "58	544	0x372b15ff	0xc8d4eaff\n"
                + "60	589	0x2a2119ff	0xd5dee6ff\n"
                + "63	110	0x484232ff	0xb7bdcdff\n"
                + "64	290	0x362012ff	0xc9dfedff\n"
                + "66	541	0x493b1eff	0xb6c4e1ff\n"
                + "70	225	0x272218ff	0xd8dde7ff\n"
                + "72	106	0x433d29ff	0xbcc2d6ff\n"
                + "76	370	0x211911ff	0xdee6eeff\n"
                + "81	493	0x32291aff	0xcdd6e5ff\n"
                + "82	614	0x2a2418ff	0xd5dbe7ff\n"
                + "83	310	0x5f3d23ff	0xa0c2dcff\n"
                + "83	694	0x5f3d23ff	0xa0c2dcff\n"
                + "83	733	0x2c2019ff	0xd3dfe6ff\n"
                + "84	241	0x282016ff	0xd7dfe9ff\n"
                + "86	325	0x8c572aff	0x73a8d5ff\n"
                + "88	418	0x797555ff	0x868aaaff\n"
                + "90	636	0x261f15ff	0xd9e0eaff\n"
                + "93	647	0x211c18ff	0xdee3e7ff\n"
                + "94	461	0x48362bff	0xb7c9d4ff\n"
                + "97	391	0x515149ff	0xaeaeb6ff\n"
                + "97	632	0x272018ff	0xd8dfe7ff\n"
                + "98	645	0x201b15ff	0xdfe4eaff\n"
                + "99	103	0xbf884fff	0x4077b0ff\n"
                + "99	469	0x654228ff	0x9abdd7ff\n"
                + "100	643	0x241f19ff	0xdbe0e6ff\n"
                + "102	417	0x7e7856ff	0x8187a9ff\n"
                + "102	500	0x845833ff	0x7ba7ccff\n"
                + "106	262	0x211a14ff	0xdee5ebff\n"
                + "106	611	0x2f2619ff	0xd0d9e6ff\n"
                + "107	629	0x272016ff	0xd8dfe9ff\n"
                + "108	19	0x5e5b47ff	0xa1a4b8ff\n"
                + "115	141	0x251d1aff	0xdae2e5ff\n"
                + "116	56	0xb07d3eff	0x4f82c1ff\n"
                + "127	675	0x1d1711ff	0xe2e8eeff\n"
                + "129	359	0x1a140aff	0xe5ebf5ff\n"
                + "130	602	0x382613ff	0xc7d9ecff\n"
                + "130	684	0x4c2f1dff	0xb3d0e2ff\n"
                + "131	143	0x34251cff	0xcbdae3ff\n"
                + "131	659	0x201810ff	0xdfe7efff\n"
                + "132	270	0x1b1611ff	0xe4e9eeff\n"
                + "133	35	0x736953ff	0x8c96acff\n"
                + "133	171	0xc89b58ff	0x3764a7ff\n"
                + "134	551	0xc69459ff	0x396ba6ff\n"
                + "135	5	0x4b5043ff	0xb4afbcff\n"
                + "136	2	0x3c3f2aff	0xc3c0d5ff\n"
                + "138	52	0x453e32ff	0xbac1cdff\n"
                + "139	402	0x666756ff	0x9998a9ff\n"
                + "141	387	0x3f4433ff	0xc0bbccff\n"
                + "143	262	0x201d16ff	0xdfe2e9ff\n"
                + "145	73	0x7b593aff	0x84a6c5ff\n"
                + "146	728	0x22180eff	0xdde7f1ff\n"
                + "147	65	0x413428ff	0xbecbd7ff\n"
                + "148	488	0x4c3c2cff	0xb3c3d3ff\n"
                + "150	573	0xa87541ff	0x578abeff\n"
                + "150	670	0x16120fff	0xe9edf0ff\n"
                + "151	459	0x3d3425ff	0xc2cbdaff\n"
                + "154	362	0x1a130dff	0xe5ecf2ff\n"
                + "155	174	0x764c2cff	0x89b3d3ff\n"
                + "158	327	0x35210fff	0xcadef0ff\n"
                + "160	139	0x2f2621ff	0xd0d9deff\n"
                + "162	28	0x777567ff	0x888a98ff\n"
                + "163	725	0x251a13ff	0xdae5ecff\n"
                + "165	338	0x24180cff	0xdbe7f3ff\n"
                + "166	110	0x3d3735ff	0xc2c8caff\n"
                + "167	735	0x211612ff	0xdee9edff\n"
                + "170	50	0x7c795aff	0x8386a5ff\n"
                + "170	194	0x644324ff	0x9bbcdbff\n"
                + "175	48	0x7a7964ff	0x85869bff\n"
                + "175	362	0x1b110fff	0xe4eef0ff\n"
                + "177	747	0x1c110aff	0xe3eef5ff\n"
                + "181	486	0x4c4532ff	0xb3bacdff\n"
                + "181	670	0x20150fff	0xdfeaf0ff\n"
                + "184	651	0x23180eff	0xdce7f1ff\n"
                + "189	75	0x5c5b4aff	0xa3a4b5ff\n"
                + "190	510	0x3f3c31ff	0xc0c3ceff\n"
                + "190	551	0x261c13ff	0xd9e3ecff\n"
                + "191	281	0x24170eff	0xdbe8f1ff\n"
                + "191	325	0x2e1c0eff	0xd1e3f1ff\n"
                + "191	434	0x727057ff	0x8d8fa8ff\n"
                + "192	5	0x595743ff	0xa6a8bcff\n"
                + "192	531	0x3a382eff	0xc5c7d1ff\n"
                + "194	506	0x404036ff	0xbfbfc9ff\n"
                + "195	142	0x3c3934ff	0xc3c6cbff\n"
                + "196	208	0x1d1412ff	0xe2ebedff\n"
                + "196	586	0x1a130dff	0xe5ecf2ff\n"
                + "197	209	0x27201aff	0xd8dfe5ff\n"
                + "198	555	0x2b1f11ff	0xd4e0eeff\n"
                + "199	75	0x646a4fff	0x9b95b0ff\n"
                + "199	509	0x333427ff	0xcccbd8ff\n"
                + "201	633	0x221914ff	0xdde6ebff\n"
                + "203	146	0x363529ff	0xc9cad6ff\n"
                + "203	159	0x2f2819ff	0xd0d7e6ff\n"
                + "203	409	0x6e6b5aff	0x9194a5ff\n"
                + "204	476	0x444639ff	0xbbb9c6ff\n"
                + "208	15	0x717265ff	0x8e8d9aff\n"
                + "209	743	0x1d1411ff	0xe2ebeeff\n"
                + "210	179	0x3d2d16ff	0xc2d2e9ff\n"
                + "212	322	0x1b120dff	0xe4edf2ff\n"
                + "214	337	0x1c120bff	0xe3edf4ff\n"
                + "215	671	0x241914ff	0xdbe6ebff\n"
                + "216	719	0x1d130eff	0xe2ecf1ff\n"
                + "217	715	0x19140eff	0xe6ebf1ff\n"
                + "218	498	0x343527ff	0xcbcad8ff\n"
                + "222	326	0x231811ff	0xdce7eeff\n"
                + "225	702	0x1a120bff	0xe5edf4ff\n"
                + "226	237	0x2f2116ff	0xd0dee9ff\n"
                + "226	767	0x171211ff	0xe8edeeff\n"
                + "227	185	0x553c1dff	0xaac3e2ff\n"
                + "230	515	0x383628ff	0xc7c9d7ff\n"
                + "233	575	0x382013ff	0xc7dfecff\n"
                + "241	128	0x3a3126ff	0xc5ced9ff\n"
                + "241	671	0x1d130bff	0xe2ecf4ff\n"
                + "245	216	0x4c2a1eff	0xb3d5e1ff\n"
                + "247	393	0x5f5f4fff	0xa0a0b0ff\n"
                + "251	274	0x332113ff	0xccdeecff\n"
                + "252	484	0x656148ff	0x9a9eb7ff\n"
                + "253	9	0x5e5e4bff	0xa1a1b4ff\n"
                + "255	696	0x291a11ff	0xd6e5eeff\n"
                + "256	561	0x543a22ff	0xabc5ddff\n"
                + "257	59	0x423e35ff	0xbdc1caff\n"
                + "268	630	0x3d2c24ff	0xc2d3dbff\n"
                + "275	518	0x3f3929ff	0xc0c6d6ff\n"
                + "280	111	0x484730ff	0xb7b8cfff\n"
                + "282	40	0x7b7b63ff	0x84849cff\n"
                + "282	112	0x474530ff	0xb8bacfff\n"
                + "282	280	0x2b2019ff	0xd4dfe6ff\n"
                + "283	487	0x413f2fff	0xbec0d0ff\n"
                + "283	626	0x533923ff	0xacc6dcff\n"
                + "283	654	0x36241aff	0xc9dbe5ff\n"
                + "288	162	0x523f28ff	0xadc0d7ff\n"
                + "289	607	0x633d2bff	0x9cc2d4ff\n"
                + "289	758	0x261c13ff	0xd9e3ecff\n"
                + "292	60	0x8f8a69ff	0x707596ff\n"
                + "294	59	0x948e6eff	0x6b7191ff\n"
                + "294	355	0x231a11ff	0xdce5eeff\n"
                + "294	357	0x231a13ff	0xdce5ecff\n"
                + "294	634	0x2e2017ff	0xd1dfe8ff\n"
                + "296	235	0x7f542fff	0x80abd0ff\n"
                + "298	591	0x623f24ff	0x9dc0dbff\n"
                + "299	412	0x7a7965ff	0x85869aff\n"
                + "300	405	0x6e6c57ff	0x9193a8ff\n"
                + "301	515	0x413826ff	0xbec7d9ff\n"
                + "302	507	0x3d3824ff	0xc2c7dbff\n"
                + "304	329	0x2b1e15ff	0xd4e1eaff\n"
                + "305	721	0x2a1e15ff	0xd5e1eaff\n"
                + "308	120	0x474230ff	0xb8bdcfff\n"
                + "309	328	0x281d17ff	0xd7e2e8ff\n"
                + "313	444	0x8b805cff	0x747fa3ff\n"
                + "314	103	0x4d4637ff	0xb2b9c8ff\n"
                + "318	207	0x2e221eff	0xd1dde1ff\n"
                + "319	629	0x352619ff	0xcad9e6ff\n"
                + "324	135	0x464238ff	0xb9bdc7ff\n"
                + "324	555	0x372919ff	0xc8d6e6ff\n"
                + "325	581	0x2f2823ff	0xd0d7dcff\n"
                + "326	264	0x31271cff	0xced8e3ff\n"
                + "326	272	0x2b211dff	0xd4dee2ff\n"
                + "327	471	0xabab92ff	0x54546dff\n"
                + "328	392	0x5d5d54ff	0xa2a2abff\n"
                + "330	601	0x30241eff	0xcfdbe1ff\n"
                + "331	154	0x565040ff	0xa9afbfff\n"
                + "333	100	0x4c4938ff	0xb3b6c7ff\n"
                + "333	179	0x28211cff	0xd7dee3ff\n"
                + "335	227	0x312820ff	0xced7dfff\n"
                + "335	668	0xb9854dff	0x467ab2ff\n"
                + "338	131	0x433323ff	0xbcccdcff\n"
                + "338	202	0x3d2b1fff	0xc2d4e0ff\n"
                + "340	349	0x2c2019ff	0xd3dfe6ff\n"
                + "340	667	0xaa7542ff	0x558abdff\n"
                + "341	27	0x666452ff	0x999badff\n"
                + "342	673	0xba8249ff	0x457db6ff\n"
                + "345	151	0x493329ff	0xb6ccd6ff\n"
                + "345	495	0x39261aff	0xc6d9e5ff\n"
                + "346	73	0x47321fff	0xb8cde0ff\n"
                + "346	94	0x39281bff	0xc6d7e4ff\n"
                + "347	101	0x43322bff	0xbccdd4ff\n"
                + "348	476	0x32281fff	0xcdd7e0ff\n"
                + "348	478	0x2c1f17ff	0xd3e0e8ff\n"
                + "349	720	0x4a2a15ff	0xb5d5eaff\n"
                + "350	411	0x716e56ff	0x8e91a9ff\n"
                + "351	559	0x42281cff	0xbdd7e3ff\n"
                + "351	612	0x261d11ff	0xd9e2eeff\n"
                + "351	736	0x382619ff	0xc7d9e6ff\n"
                + "352	168	0x241915ff	0xdbe6eaff\n"
                + "352	227	0x2c2017ff	0xd3dfe8ff\n"
                + "352	662	0x2d241aff	0xd2dbe5ff\n"
                + "354	307	0xa1663aff	0x5e99c5ff\n"
                + "355	292	0xa36d3eff	0x5c92c1ff\n"
                + "356	239	0x211c12ff	0xdee3edff\n"
                + "356	705	0x6b4122ff	0x94beddff\n"
                + "356	712	0x52351eff	0xadcae1ff\n"
                + "357	160	0x28221bff	0xd7dde4ff\n"
                + "359	601	0x503e28ff	0xafc1d7ff\n"
                + "361	3	0x646053ff	0x9b9facff\n"
                + "363	141	0x29211cff	0xd6dee3ff\n"
                + "363	537	0x231c13ff	0xdce3ecff\n"
                + "364	403	0x625e40ff	0x9da1bfff\n"
                + "365	31	0x76715eff	0x898ea1ff\n"
                + "367	174	0xc69c56ff	0x3963a9ff\n"
                + "372	510	0x925e31ff	0x6da1ceff\n"
                + "373	311	0x935c32ff	0x6ca3cdff\n"
                + "374	620	0x362d1cff	0xc9d2e3ff\n"
                + "374	647	0x231e14ff	0xdce1ebff\n"
                + "377	320	0x7e4a28ff	0x81b5d7ff\n"
                + "378	31	0x727064ff	0x8d8f9bff\n"
                + "381	70	0xd09d5eff	0x2f62a1ff\n"
                + "382	0	0x34321aff	0xcbcde5ff\n"
                + "383	117	0x674326ff	0x98bcd9ff\n"
                + "383	496	0xc08854ff	0x3f77abff\n"
                + "383	513	0x865b39ff	0x79a4c6ff\n"
                + "385	93	0xa16d3aff	0x5e92c5ff\n"
                + "385	697	0x552f1aff	0xaad0e5ff\n"
                + "387	314	0x5d3721ff	0xa2c8deff\n"
                + "391	156	0x825f37ff	0x7da0c8ff\n"
                + "391	675	0x15120cff	0xeaedf3ff\n"
                + "392	86	0x593920ff	0xa6c6dfff\n"
                + "392	621	0x2b2515ff	0xd4daeaff\n"
                + "394	695	0x753f1dff	0x8ac0e2ff\n"
                + "395	205	0xc19256ff	0x3e6da9ff\n"
                + "395	461	0xb18155ff	0x4e7eaaff\n"
                + "398	757	0x1a1309ff	0xe5ecf6ff\n"
                + "399	669	0x1e1913ff	0xe1e6ecff\n"
                + "401	272	0x1b1610ff	0xe4e9efff\n"
                + "402	758	0x18130dff	0xe7ecf2ff\n"
                + "403	506	0x392717ff	0xc6d8e8ff\n"
                + "404	490	0x4b351eff	0xb4cae1ff\n"
                + "404	651	0x211b11ff	0xdee4eeff\n"
                + "405	506	0x362920ff	0xc9d6dfff\n"
                + "406	447	0x3c3229ff	0xc3cdd6ff\n"
                + "406	629	0x241b15ff	0xdbe4eaff\n"
                + "409	26	0x6f6e63ff	0x90919cff\n"
                + "410	39	0x787763ff	0x87889cff\n"
                + "412	125	0x3a3531ff	0xc5caceff\n"
                + "412	364	0x1e170cff	0xe1e8f3ff\n"
                + "412	650	0x231d19ff	0xdce2e6ff\n"
                + "412	673	0x1e1812ff	0xe1e7edff\n"
                + "414	96	0x2f2a29ff	0xd0d5d6ff\n"
                + "416	88	0x382e25ff	0xc7d1daff\n"
                + "418	580	0x765a33ff	0x89a5ccff\n"
                + "418	691	0x3e200cff	0xc1dff3ff\n"
                + "419	306	0x3a2010ff	0xc5dfefff\n"
                + "420	254	0x211b17ff	0xdee4e8ff\n"
                + "421	198	0x442c17ff	0xbbd3e8ff\n"
                + "421	319	0x422109ff	0xbddef6ff\n"
                + "422	485	0x39312cff	0xc6ced3ff\n"
                + "425	726	0x1e1611ff	0xe1e9eeff\n"
                + "426	242	0x2a2017ff	0xd5dfe8ff\n"
                + "427	446	0x402f22ff	0xbfd0ddff\n"
                + "428	455	0x2b281eff	0xd4d7e1ff\n"
                + "428	483	0x2f2721ff	0xd0d8deff\n"
                + "428	640	0x1e1610ff	0xe1e9efff\n"
                + "429	96	0x373028ff	0xc8cfd7ff\n"
                + "429	547	0x443022ff	0xbbcfddff\n"
                + "431	375	0x1f1813ff	0xe0e7ecff\n"
                + "431	572	0x5f4027ff	0xa0bfd8ff\n"
                + "433	51	0x7e7d62ff	0x81829dff\n"
                + "433	104	0x261e13ff	0xd9e1ecff\n"
                + "436	123	0x332c23ff	0xccd3dcff\n"
                + "437	638	0x201813ff	0xdfe7ecff\n"
                + "439	677	0x231b17ff	0xdce4e8ff\n"
                + "440	86	0x474741ff	0xb8b8beff\n"
                + "442	660	0x251b11ff	0xdae4eeff\n"
                + "443	417	0x6f6a4fff	0x9095b0ff\n"
                + "443	531	0x322e29ff	0xcdd1d6ff\n"
                + "443	667	0x21160fff	0xdee9f0ff\n"
                + "446	682	0x21140eff	0xdeebf1ff\n"
                + "449	578	0x261d17ff	0xd9e2e8ff\n"
                + "449	587	0x221b16ff	0xdde4e9ff\n"
                + "453	263	0x21170eff	0xdee8f1ff\n"
                + "455	242	0x1d0f07ff	0xe2f0f8ff\n"
                + "455	352	0x1f1711ff	0xe0e8eeff\n"
                + "455	636	0x1f120bff	0xe0edf4ff\n"
                + "455	709	0x2c1b10ff	0xd3e4efff\n"
                + "456	383	0x1b1612ff	0xe4e9edff\n"
                + "459	417	0x746e5aff	0x8b91a5ff\n"
                + "460	534	0x504e43ff	0xafb1bcff\n"
                + "461	426	0x696854ff	0x9697abff\n"
                + "464	683	0x1e150fff	0xe1eaf0ff\n"
                + "465	661	0x21130cff	0xdeecf3ff\n"
                + "467	697	0x20140dff	0xdfebf2ff\n"
                + "469	61	0x817d5fff	0x7e82a0ff\n"
                + "469	344	0x1e130dff	0xe1ecf2ff\n"
                + "470	532	0x434237ff	0xbcbdc8ff\n"
                + "473	301	0x1d130cff	0xe2ecf3ff\n"
                + "473	303	0x2f2720ff	0xd0d8dfff\n"
                + "474	67	0x918b5dff	0x6e74a2ff\n"
                + "476	472	0x8e8757ff	0x7178a8ff\n"
                + "478	622	0x281913ff	0xd7e6ecff\n"
                + "480	206	0x2f2118ff	0xd0dee7ff\n"
                + "483	418	0x7c7d67ff	0x838298ff\n"
                + "484	582	0x442717ff	0xbbd8e8ff\n"
                + "485	61	0x7a7759ff	0x8588a6ff\n"
                + "485	215	0x291c15ff	0xd6e3eaff\n"
                + "485	251	0x271a12ff	0xd8e5edff\n"
                + "485	331	0x271811ff	0xd8e7eeff\n"
                + "487	550	0x53441dff	0xacbbe2ff\n"
                + "488	691	0x221712ff	0xdde8edff\n"
                + "493	141	0x3e3c2eff	0xc1c3d1ff\n"
                + "493	316	0x22150dff	0xddeaf2ff\n"
                + "495	401	0x6f6e58ff	0x9091a7ff\n"
                + "495	499	0x3e3c28ff	0xc1c3d7ff\n"
                + "497	301	0x241b13ff	0xdbe4ecff\n"
                + "498	40	0x79755bff	0x868aa4ff\n"
                + "500	316	0x201913ff	0xdfe6ecff\n"
                + "500	414	0x686956ff	0x9796a9ff\n"
                + "500	569	0x6d4a23ff	0x92b5dcff\n"
                + "502	152	0x44381fff	0xbbc7e0ff\n"
                + "504	529	0x4b4839ff	0xb4b7c6ff\n"
                + "505	99	0x656041ff	0x9a9fbeff\n"
                + "505	433	0x79785dff	0x8687a2ff\n"
                + "506	238	0x613f24ff	0x9ec0dbff\n"
                + "506	663	0x25170eff	0xdae8f1ff\n"
                + "507	251	0x322014ff	0xcddfebff\n"
                + "507	331	0x341d0fff	0xcbe2f0ff\n"
                + "507	423	0x73725bff	0x8c8da4ff\n"
                + "507	513	0x403d2dff	0xbfc2d2ff\n"
                + "509	217	0x3a2617ff	0xc5d9e8ff\n"
                + "509	366	0x281a11ff	0xd7e5eeff\n"
                + "510	669	0x261c11ff	0xd9e3eeff\n"
                + "513	218	0x332217ff	0xccdde8ff").split("\n"));
    }
}
