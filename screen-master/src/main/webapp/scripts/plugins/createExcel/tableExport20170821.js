
		var idTmr;
		function  getExplorer() {
			var explorer = window.navigator.userAgent ;
			//ie 
			if (explorer.indexOf("MSIE") >= 0) {
				return 'ie';
			}
			//firefox 
			else if (explorer.indexOf("Firefox") >= 0) {
				return 'Firefox';
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
		}
        function method1(tableid) {//Õû¸ö±í¸ñ¿½±´µ½EXCELÖÐ
			if(getExplorer()=='ie')
			{
				var curTbl = document.getElementById(tableid);
				var oXL = new ActiveXObject("Excel.Application");
				//´´½¨AX¶ÔÏóexcel 
				var oWB = oXL.Workbooks.Add();
				//»ñÈ¡workbook¶ÔÏó 
				var xlsheet = oWB.Worksheets(1);
				//¼¤»îµ±Ç°sheet 
				var sel = document.body.createTextRange();
				sel.moveToElementText(curTbl);
				//°Ñ±í¸ñÖÐµÄÄÚÈÝÒÆµ½TextRangeÖÐ 
				sel.select();
				//È«Ñ¡TextRangeÖÐÄÚÈÝ 
				sel.execCommand("Copy");
				//¸´ÖÆTextRangeÖÐÄÚÈÝ  
				xlsheet.Paste();
				//Õ³Ìùµ½»î¶¯µÄEXCELÖÐ       
				oXL.Visible = true;
				//ÉèÖÃexcel¿É¼ûÊôÐÔ

				try {
					var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");
				} catch (e) {
					print("Nested catch caught " + e);
				} finally {
					oWB.SaveAs(fname);

					oWB.Close(savechanges = false);
					//xls.visible = false;
					oXL.Quit();
					oXL = null;
					//½áÊøexcel½ø³Ì£¬ÍË³öÍê³É
					//window.setInterval("Cleanup();",1);
					idTmr = window.setInterval("Cleanup();", 1);

				}
				
			}
			else
			{
				tableToExcel(tableid)
			}
        }
        function Cleanup() {
            window.clearInterval(idTmr);
            CollectGarbage();
        }
		var tableToExcel = (function() {
			  var uri = 'data:application/vnd.ms-excel;base64,',
			  template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
				base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },
				format = function(s, c) {
					return s.replace(/{(\w+)}/g,
					function(m, p) { return c[p]; }) }
				return function(table, name) {     //excel表格的名称（左下角）
				if (!table.nodeType) table = document.getElementById(table)
				var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
				window.location.href = uri + base64(format(template, ctx))
			  }
			})();
			
		
			