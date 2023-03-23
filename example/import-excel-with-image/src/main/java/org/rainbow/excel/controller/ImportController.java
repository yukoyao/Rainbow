package org.rainbow.excel.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.HashBasedTable;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.rainbow.excel.entity.ExcelData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author K
 */
@RestController
public class ImportController {


  @PostMapping("importGoods")
  public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
    try {
      InputStream inputStream = file.getInputStream();

      // 创建Excel工作簿
      Workbook workbook = WorkbookFactory.create(inputStream);

      // 获取第一个Sheet
      Sheet sheet = workbook.getSheetAt(0);

      // 从第二行开始读取数据，第一行是表头
      int rowNum = 1;

      // 读取所有的图片
      HashBasedTable<Integer, Integer, List<byte[]>> allPictures = getAllPictures(workbook, sheet);

      List<ExcelData> materialList = new ArrayList<>();

      while (rowNum <= sheet.getLastRowNum()) {
        Row row = sheet.getRow(rowNum++);

        if (getCellValue(row, 0) == null) {
          break;
        }

        ExcelData material = new ExcelData();
        material.setName(StrUtil.toString(getCellValue(row, 0)));
        material.setUnit(StrUtil.toString(getCellValue(row, 1)));
        material.setBrand(StrUtil.toString(getCellValue(row, 2)));
        material.setModel(StrUtil.toString(getCellValue(row, 3)));
        material.setSn(StrUtil.toString(getCellValue(row, 4)));
        material.setInfo(StrUtil.toString(getCellValue(row, 5)));
        material.setPrice(getCellValue(row, 6) == null ? BigDecimal.ZERO
            : new BigDecimal(StrUtil.toString(getCellValue(row, 6))));
        material.setYearPurchase(getCellValue(row, 7) == null ? 0
            : Integer.parseInt(StrUtil.toString(getCellValue(row, 7))));
        material.setRemark(StrUtil.toString(getCellValue(row, 8)));

        List<String> picUrls = new ArrayList<>();
        List<byte[]> picByteList = allPictures.get(rowNum - 1, 9);
        if(CollUtil.isNotEmpty(picByteList)) {
          for (final byte[] bytes : picByteList) {
            picUrls.add(saveImage(bytes, RandomUtil.randomString(10) + ".jpg"));
          }
        }
        material.setPicUrls(picUrls);
        material.setCategory1(StrUtil.toString(getCellValue(row, 10)));
        material.setCategory2(StrUtil.toString(getCellValue(row, 11)));
        material.setCategory3(StrUtil.toString(getCellValue(row, 12)));
        material.setDeleteFlag(StrUtil.toString(getCellValue(row, 13)));
        material.setType(StrUtil.toString(getCellValue(row, 14)));

        materialList.add(material);
      }

      // 关闭工作簿
      workbook.close();

      // 批量保存物料信息
      // ...

      return ResponseEntity.ok("Excel导入成功");
    } catch (IOException | EncryptedDocumentException ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Excel导入失败：" + ex.getMessage());
    }
  }

  public HashBasedTable<Integer, Integer, List<byte[]>> getAllPictures(Workbook workbook, Sheet sheet) {
    int lastRowNum = sheet.getLastRowNum();
    int leftCol = 15;
    HashBasedTable<Integer, Integer, List<byte[]>> tables = HashBasedTable.create();
    // 初始化容器
    for (int i = 1; i < lastRowNum; i++) {
      for (int j = 0; j < leftCol; j++) {
        tables.put(i, j, new ArrayList<>());
      }
    }

    if (workbook instanceof XSSFWorkbook) {
      XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
      List<? extends PictureData> pictures = workbook.getAllPictures();

      for (PictureData picture : pictures) {
        byte[] data = picture.getData();
        int idx = workbook.getAllPictures().indexOf(picture);
        XSSFShape shape = drawing.getShapes().get(idx);
        XSSFClientAnchor anchor = (XSSFClientAnchor) shape.getAnchor();
        int row = anchor.getRow1();
        int col1 = anchor.getCol1();
        List<byte[]> bytes = tables.get(row, col1);
        bytes.add(data);
      }
    } else if (workbook instanceof HSSFWorkbook) {
      HSSFPatriarch drawing = (HSSFPatriarch) sheet.createDrawingPatriarch();
      List<HSSFShape> shapes = drawing.getChildren();

      for (HSSFShape shape : shapes) {
        if (shape instanceof HSSFPicture) {
          HSSFPicture picture = (HSSFPicture) shape;
          byte[] data = picture.getPictureData().getData();
          HSSFClientAnchor anchor = (HSSFClientAnchor) picture.getClientAnchor();
          int row = anchor.getRow1();
          int col1 = anchor.getCol1();
          List<byte[]> bytes = tables.get(row, col1);
          bytes.add(data);
        }
      }
    }
    return tables;
  }

  public Object getCellValue(Row row, int colIndex) {
    Cell cell = row.getCell(colIndex);
    if (cell != null) {
      if (cell.getCellType() == CellType.NUMERIC) {
        return cell.getNumericCellValue();
      } else if (cell.getCellType() == CellType.STRING) {
        return cell.getStringCellValue();
      } else if (cell.getCellType() == CellType.BOOLEAN) {
        return cell.getBooleanCellValue();
      } else if (cell.getCellType() == CellType.FORMULA) {
        return cell.getCellFormula();
      } else {
        return null;
      }
    }
    return null;
  }

  public String saveImage(byte[] imageData, String fileName) {
    String path = "D://images/" + fileName;
    FileUtil.writeBytes(imageData, path);
    return path;
  }

}
