package me.excel.tools.factory;

import me.excel.tools.utils.CellValueConverter;
import me.excel.tools.utils.ValueExtractor;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * file factory
 *
 * Created by hanwen on 15-12-16.
 */
public interface UserFileFactory {

  /**
   * 设置file中的titles (第一行)
   *
   * @param titles
   */
  void setTitles(String... titles);

  /**
   * 设置file中的fields (第二行)
   *
   * @param fields
   */
  void setFields(String... fields);

  /**
   * 设置file中的data (从第四行开始)
   *
   * @param data
   */
  void setData(List data);

  void setValueExtractor(ValueExtractor valueExtractor);

  /**
   * 设置自定义的转换器
   *
   * @param converters
   * @see  me.excel.tools.model.excel.ExcelCell#convertToReadableValue(String)
   */
  void addCellValueConverter(CellValueConverter... converters);

  /**
   * 生成文件
   *
   * @param file
   * @throws IOException
   */
  void generate(File file) throws IOException;

  void generate(File excel, boolean createTitles, boolean createFields, boolean createPrompts) throws IOException;
}
