import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private double[] lonArr;

    public Rasterer() {
        // YOUR CODE HERE
        lonArr = new double[8];
        lonArr[0] = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / 256;
        for (int i = 1; i < lonArr.length; i++) {
            lonArr[i] = lonArr[i - 1] / 2;
        }

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    //ul左上，lon纵坐标，lat横坐标
    public boolean checkOutOfRange(double ullat, double ullon,
                                   double lrlat, double lrlon) {
        if (ullat < lrlat || lrlon < ullon) {
            return true;
        }
        return MapServer.ROOT_ULLON > lrlon || ullon > MapServer.ROOT_LRLON
                || MapServer.ROOT_LRLAT > ullat || lrlat > MapServer.ROOT_ULLAT;
    }

    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        double ullat = params.get("ullat");
        double ullon = params.get("ullon");
        double lrlat = params.get("lrlat");
        double lrlon = params.get("lrlon");
        double w = params.get("w");
        double h = params.get("h");
        double targetLon = (lrlon - ullon) / w;
        int depth = -1;
        if (checkOutOfRange(ullat, ullon, lrlat, lrlon)) {
            results.put("query_success", false);
            return results;
        }
        for (int i = 0; i < lonArr.length; i++) {
            if (lonArr[i] <= targetLon) {
                depth = i;
                break;
            }
        }
        if (depth == -1) {
            depth = 7;
        }
        results.put("depth", depth);
        results.put("query_success", true);
        ullat = ullat <= MapServer.ROOT_ULLAT ? ullat : MapServer.ROOT_ULLAT;
        lrlat = lrlat >= MapServer.ROOT_LRLAT ? lrlat : MapServer.ROOT_LRLAT;
        lrlon = lrlon <= MapServer.ROOT_LRLON ? lrlon : MapServer.ROOT_LRLON;
        ullon = ullon >= MapServer.ROOT_ULLON ? ullon : MapServer.ROOT_ULLON;
        int num = (int) Math.pow(2, depth);
        double xItem = ((MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / num);
        double yItem = ((MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / num);
        int yLow = (int) ((MapServer.ROOT_ULLAT - ullat) / yItem);
        int yHigh = (int) ((MapServer.ROOT_ULLAT - lrlat) / yItem);
        int xLow = (int) ((ullon - MapServer.ROOT_ULLON) / xItem);
        int xHigh = (int) ((lrlon - MapServer.ROOT_ULLON) / xItem);
        results.put("raster_ul_lon", MapServer.ROOT_ULLON + xItem * xLow);
        results.put("raster_ul_lat", MapServer.ROOT_ULLAT - yLow * yItem);
        results.put("raster_lr_lon", MapServer.ROOT_ULLON + xItem * (xHigh + 1));
        results.put("raster_lr_lat", MapServer.ROOT_ULLAT - (yHigh + 1) * yItem);
        String[][] pics = new String[yHigh - yLow + 1][xHigh - xLow + 1];
        for (int i = 0; i < pics.length; i++) {
            for (int j = 0; j < pics[i].length; j++) {
                pics[i][j] = "d" + depth + "_x" + (j + xLow)
                        + "_y" + (i + yLow) + ".png";
                //System.out.println(pics[i][j]);
            }
        }
        results.put("render_grid", pics);
        //System.out.println(results);
        return results;
    }


}


