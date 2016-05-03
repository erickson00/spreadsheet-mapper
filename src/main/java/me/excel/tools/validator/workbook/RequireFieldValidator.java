package me.excel.tools.validator.workbook;

import me.excel.tools.model.excel.ExcelCell;
import me.excel.tools.model.excel.ExcelWorkbook;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hanwen on 4/26/16.
 */
public class RequireFieldValidator implements WorkbookValidator {

  protected List<String> requireFields = new ArrayList<>();

  protected ExcelWorkbook excelWorkbook;

  public RequireFieldValidator(List<String> requireFields) {
    this.requireFields = requireFields;
  }

  @Override
  public String getErrorMessage() {
    if (this.excelWorkbook == null) {
      return "";
    }
    return "不包含所要求的字段:" + StringUtils.join(getLostFields(this.excelWorkbook), ",");
  }

  @Override
  public ExcelCell getMessageOnCell(ExcelWorkbook excelWorkbook) {
    return excelWorkbook.getFirstSheet().getRow(0).getCell(0);
  }

  @Override
  public boolean validate(ExcelWorkbook excelWorkbook) {
    boolean result = getLostFields(excelWorkbook).isEmpty();
    if (!result) {
      this.excelWorkbook = excelWorkbook;
      return false;
    }
    return true;
  }

  private List<String> getLostFields(ExcelWorkbook excelWorkbook) {

    List<String> keyRowFields = excelWorkbook.getFirstSheet().getKeyRowFields();
    return requireFields.stream()
        .filter(requiredField -> !keyRowFields.contains(requiredField))
        .collect(Collectors.toList());
  }
}