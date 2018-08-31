var idTmr;
function  getExplorer() {
	var explorer = window.navigator.userAgent ;
	//firefox
	if (explorer.indexOf("Firefox") >= 0) {
		return 'Firefox';
	}
	//ie
	else if (explorer.indexOf("rv:") >= 0||explorer.indexOf("MSIE") >= 0) {
		return 'ie';
	}
	//Chrome
	else if(explorer.indexOf("Chrome") >= 0){
		return 'Chrome';
	}
	//Opera
	else if(explorer.indexOf("Opera") >= 0){
		return 'Opera';
	}
	//Safari
	else if(explorer.indexOf("Safari") >= 0){
		return 'Safari';
	}
	//Edge
	else if(explorer.indexOf("Edge") >= 0){
		return 'Edge';
	}
}

function exportExcel(tableid,filename) {
	var table=document.getElementById(tableid);
	table.setAttribute('style','display:none;border-collapse: collapse;border:#ddd 1px solid;');
	table.setAttribute('border','1');
	var td=table.getElementsByTagName("td");
	for(var i=0;i<td.length;i++){
		td[i].setAttribute('style','border:#ddd 1px solid;');
	}
	var th=table.getElementsByTagName("th");
	for(var i=0;i<th.length;i++){
		th[i].setAttribute('style','border:#ddd 1px solid;');
	}
	var html = "<html><head><meta charset='utf-8' /></head><body>" + document.getElementById(tableid).outerHTML + "</body></html>";
	var blob = new Blob([html], { type: "application/vnd.ms-excel" });
	var a = document.getElementById("dlink");
	if(getExplorer()=='ie'||getExplorer()=='Edge'){
		window.navigator.msSaveOrOpenBlob(blob, filename+".xls");
	}else{
		a.href = URL.createObjectURL(blob);
		a.download = filename+".xls";
		document.getElementById("dlink").click();
	}

}
function method5(tableid,name,filename,iename) {
	if(getExplorer()=='ie')
	{
		BJUI.alertmsg('info', '检测到您使用的是IE浏览器,请您保证在Internet选项的安全项中选择低级别!',{
			okCall:function () {
				var curTbl = document.getElementById(iename);
				var oXL = new ActiveXObject("Excel.Application");
				var oWB = oXL.Workbooks.Add();
				var xlsheet = oWB.Worksheets(1);
				var sel = document.body.createTextRange();
				sel.moveToElementText(curTbl);
				sel.select();
				sel.execCommand("Copy");
				xlsheet.Paste();

				try {
					var fname = oXL.Application.GetSaveAsFilename(filename+".xls", "Excel Spreadsheets (*.xls), *.xls");
				} catch (e) {
					print("Nested catch caught " + e);
				} finally {
					oWB.SaveAs(fname);
					oWB.Close(savechanges = false);
					oXL.Quit();
					oXL = null;
					idTmr = window.setInterval("Cleanup();", 1);
					// oXL.Visible = true;
				}
				BJUI.alertmsg('ok', '导出成功');
			}
		});
	}
	else
	{
		tableToExcel(tableid,name,filename)
	}
}
function Cleanup() {
	window.clearInterval(idTmr);
	CollectGarbage();
}
var tableToExcel = (function() {
	var uri = 'data:application/vnd.ms-excel;base64,',
		template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta charset="UTF-8"></head><body><table>{table}</table></body></html>',
		base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },
		format = function(s, c) {
			return s.replace(/{(\w+)}/g,
				function(m, p) { return c[p]; }) }
	return function(table, name,filename) {
		if (!table.nodeType) table = document.getElementById(table)
		var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
		document.getElementById("dlink").href = uri + base64(format(template, ctx));
		document.getElementById("dlink").download = filename;
		document.getElementById("dlink").click();
	}
})()