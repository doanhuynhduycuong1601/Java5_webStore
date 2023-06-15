package cuong.run.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;
import cuong.run.model.Orders;
import cuong.run.model.OrdersItems;
import cuong.run.service.Param;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class Bill_email {
	final Param param;
	public File billEmail(Orders orders) {
		// Tạo đối tượng XWPFDocument
		XWPFDocument document = new XWPFDocument();

		// Tạo tiêu đề hóa đơn
		XWPFParagraph title = document.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun titleRun = title.createRun();
		titleRun.setText("HÓA ĐƠN MUA HÀNG");
		titleRun.setBold(true);
		titleRun.setFontSize(16);

		// Tạo ngày tháng
		XWPFParagraph date = document.createParagraph();
		XWPFRun dateRun = date.createRun();
		dateRun.setText("Ngày đặt : " + GetString.getDateTime(orders.getOrderDate()));
		dateRun.setFontSize(12);

		// Tạo thông tin người mua
		XWPFParagraph buyerInfo = document.createParagraph();
		XWPFRun buyerRun = buyerInfo.createRun();
		buyerRun.setText("Người mua : " + orders.getCustomerID().getFullname());
		buyerRun.setFontSize(12);

		// Tạo bảng sản phẩm
		XWPFTable table = document.createTable(orders.getOrderItems().size() + 1, 4);
		table.setWidth("100%");

		// Thiết lập tiêu đề cột
		int row = 0;
		XWPFTableRow headerRow = table.getRow(row);
		headerRow.getCell(0).setText("STT");
		headerRow.getCell(1).setText("Tên sản phẩm");
		headerRow.getCell(2).setText("Giá");
		headerRow.getCell(3).setText("Quantity");

		System.out.println(orders.getOrderItems().size());
		for (OrdersItems ordersItems : orders.getOrderItems()) {
			row += 1;
			XWPFTableRow rowProduct = table.getRow(row);
			rowProduct.getCell(0).setText(String.valueOf(row));
			rowProduct.getCell(1).setText(ordersItems.getProduct().getNames());
			rowProduct.getCell(2).setText(GetString.getVnd(ordersItems.getPrice()) + " vnd");
			rowProduct.getCell(3).setText(String.valueOf(ordersItems.getQuantity()));
		}
		// Tạo thông tin người mua
		XWPFParagraph totalAmount = document.createParagraph();
		XWPFRun totalAmountRun = totalAmount.createRun();
		totalAmountRun.setText("Tổng tiền : " + GetString.getVnd(orders.getTotalAmount()) + " vnd");
		
		
		// Lưu tệp Word
		File file = new File(param.dir("/document/bill/"),orders.getOrderID()+".docx");
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            document.write(fileOut);
            System.out.println("Đã xuất hóa đơn thành công!");
        } catch (IOException e) {
            return null;
        }
        return file;
	}
}
