package main.unit;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by tangtao on 2016/4/27.
 */
public class readexcel {
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("shenfen.xls");
        Object[][] result = gettestdata(in, "test");
        return result;
    }

    public static Object[][] gettestdata(InputStream in,String sheetname) throws IOException {
        Workbook workbook=new HSSFWorkbook(in);//声明workbook对象
        Sheet sheet=workbook.getSheet(sheetname);//通过sheetname生成sheet对象
        Cell cell = null;
        int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();//获取一共有多少行幼小数据
        List<Object[]>records= new ArrayList<Object[]>();//创建名为records的list对象来储存所读取的数据
        for(int i=1;i<rowCount+1;i++){
            Row row=sheet.getRow(i);//获取行对象
            String fields[]=new String[row.getLastCellNum()-1];//声明一个数组，用来存储excel数据文件中美一行的数据由getLastCellNum()决定
            for(int columnIndex=0;columnIndex<row.getLastCellNum()-1;columnIndex++){
                //  fields[columnIndex]=row.getCell(j).getStringCellValue();//调用getcell和getstringcellvalue获取单元格的数据
                String value = "";
                cell = row.getCell(columnIndex);
                if (cell != null) {
                    // 注意：一定要设成这个，否则可能会出现乱码
//                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
//                            System.out.println("这是字符串");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:

                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                if (date != null) {
                                    value = new SimpleDateFormat("yyyy-MM-dd")
                                            .format(date);
                                } else {
                                    value = "";
                                }
                            } else {
                            	double num = cell.getNumericCellValue();
                            	double tmp = num % 1;//取余 如果是0 则为整数
                            	if (tmp == 0) {
									value = new DecimalFormat("0").format(num);
								}else{
									value = new DecimalFormat("#####0.00").format(num);
								}
                               // System.out.println(value);
                            }
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            if (!cell.getStringCellValue().equals("")) {
                                value = cell.getStringCellValue();
                            } else {
                                value = cell.getNumericCellValue() + "";
                            }
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            break;
                        case Cell.CELL_TYPE_ERROR:
                            value = "";
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            value = (cell.getBooleanCellValue() == true ? "Y"
                                    : "N");
                            break;
                        default:
                            value = "";
                    }
                }
                fields[columnIndex]=value;
                if (columnIndex == 0 && value.trim().equals("")) {
                    break;
                }
            }
            records.add(fields);
        }
        Object[][]results=new Object[records.size()][];
        for(int i=0;i<records.size();i++){
            results[i]= records.get(i);
        }
        return results;
    }

    @Test(dataProvider = "ex")
    public void tes1t(String ro1,String ro2,String ro3,String ro4){
        System.out.println(ro1+"\t"+ro2+"\t"+ro3+"\t");
    }
    
    

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
//        File file = new File("testdata.xls");
    	double a = 1.1;
    	System.out.println(a%1);
    	 a = 1.0;
    	 System.out.println(a%1 == 0);
    	
//        gettestdata(file,"login");
    }
}