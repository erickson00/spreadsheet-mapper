package com.supwisdom.spreadsheet.mapper.f2w.excel;

import com.supwisdom.spreadsheet.mapper.AssertUtil;
import com.supwisdom.spreadsheet.mapper.f2w.WorkbookReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.supwisdom.spreadsheet.mapper.model.core.Row;
import com.supwisdom.spreadsheet.mapper.model.core.Sheet;
import com.supwisdom.spreadsheet.mapper.model.core.Workbook;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.testng.Assert.assertEquals;

/**
 * Created by hanwen on 2017/1/5.
 */
@Test(groups = "Excel2WorkbookReaderTest")
public class Excel2WorkbookReaderTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(Excel2WorkbookReaderTest.class);

  @BeforeClass
  public void before() {
    LOGGER.debug("-------------------starting test workbook read helper-------------------");
  }

  @Test
  public void testRead() throws Exception {

    InputStream is1 = getClass().getResourceAsStream("test.xls");

    WorkbookReader reader = new Excel2WorkbookReader();

    Workbook workbook1 = reader.read(is1);

    AssertUtil.assertWorkbookEquals(workbook1, true);

    InputStream is2 = getClass().getResourceAsStream("test.xlsx");

    Workbook workbook2 = reader.read(is2);

    AssertUtil.assertWorkbookEquals(workbook2, true);
  }

  @Test(dependsOnMethods = "testRead")
  public void testReadDate() throws Exception {

    InputStream is1 = getClass().getResourceAsStream("dateFormatTest.xlsx");

    WorkbookReader reader = new Excel2WorkbookReader();

    Workbook workbook = reader.read(is1);

    Sheet firstSheet = workbook.getFirstSheet();

    Row firstRow = firstSheet.getFirstRow();

    // Thu Nov 22 00:00:00 CST 1984
    SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    assertEquals(sdf2.format(sdf1.parse(firstRow.getCell(1).getValue())), "1984-11-22");
    assertEquals(sdf2.format(sdf1.parse(firstRow.getCell(2).getValue())), "1984-11-22");
    assertEquals(sdf2.format(sdf1.parse(firstRow.getCell(3).getValue())), "1984-11-22");
    assertEquals(sdf2.format(sdf1.parse(firstRow.getCell(4).getValue())), "1984-11-22");

  }
}
