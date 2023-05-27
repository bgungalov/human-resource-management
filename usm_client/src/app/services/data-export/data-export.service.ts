import { throwError } from 'rxjs';
import { Injectable } from '@angular/core';
import * as XLSX from 'xlsx';

/**
 * Service for exporting data.
 */
@Injectable({
  providedIn: 'root',
})
export class DataExportService {
  dataToExport = [];

  constructor() {}

  /**
   * It takes an HTML table element and converts it to an Excel file
   * @param {string} elementId - The ID of the table you want to export.
   * @param {string} [titleDocument] - The title of the document.
   * @param {string} [sheetTitle] - The title of the sheet in the excel file.
   */
  exportTableToFile(
    elementId: string,
    titleDocument?: string,
    sheetTitle?: string
  ): void {
    if (!elementId) {
      throwError(() => new Error('Please provide element ID'));
    }
    let element = document.getElementById(elementId);
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();

    this.constructWorkBook(wb, ws, sheetTitle);

    /* save to file */
    this.saveFile(wb, titleDocument);
  }

  /**
   * It takes in an array of objects, converts it to a worksheet, constructs a workbook, and saves the
   * file
   * @param {any[]} data - any[] - The data to export.
   * @param {string} [titleDocument] - The title of the document.
   * @param {string} [sheetTitle] - The title of the sheet in the excel file.
   */
  exportJsonToFile(
    data: any[],
    titleDocument?: string,
    sheetTitle?: string
  ): void {
    if (!data) {
      throwError(() => new Error('Please provide data'));
    }

    data.forEach((eachData) => {
      const exportedData = {};
      for (const key in eachData) {
        if (Object.prototype.hasOwnProperty.call(eachData, key)) {
          const value = eachData[key];
          exportedData[this.capitalizeFirstLetter(key)] = value;
        }
      }

      this.dataToExport.push(exportedData);
    });

    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.dataToExport);

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();

    this.constructWorkBook(wb, ws, sheetTitle);

    /* save to file */
    this.saveFile(wb, titleDocument);
  }

  /**
   * It saves the file to the local machine.
   * @param wb - XLSX.WorkBook - The workbook object that we created earlier.
   * @param {string} titleDocument - The title of the document.
   */
  private saveFile(wb: XLSX.WorkBook, titleDocument: string): void {
    XLSX.writeFile(
      wb,
      `${titleDocument ? titleDocument : 'Document_Untitled'}.xlsx`
    );
  }

  /**
   * It appends a sheet to the workbook.
   * @param wb - XLSX.WorkBook - The workbook object that will be used to create the excel file.
   * @param ws - XLSX.WorkSheet - This is the worksheet that you want to add to the workbook.
   * @param {string} sheetTitle - The title of the sheet you want to create.
   */
  private constructWorkBook(
    wb: XLSX.WorkBook,
    ws: XLSX.WorkSheet,
    sheetTitle: string
  ): void {
    XLSX.utils.book_append_sheet(
      wb,
      ws,
      `${sheetTitle ? sheetTitle : 'Sheet 1'}`
    );
  }

  /**
   * It takes a string, capitalizes the first letter, and returns the new string
   * @param {string} value - string - The value that we want to capitalize.
   * @returns The first letter of the string is being capitalized and the rest of the string is being
   * returned.
   */
  private capitalizeFirstLetter(value: string): string {
    return value.charAt(0).toUpperCase() + value.slice(1);
  }
}
