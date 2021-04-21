import authHeader, { BASE_URL, HTTP } from "../http";
import axios from "axios";

export default {
  getBooks() {
    return HTTP.get(BASE_URL + "/books", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  createBook(book) {
    return HTTP.post(BASE_URL + "/books", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  editBook(book) {
    return HTTP.patch(BASE_URL + "/books/" + book.id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  deleteBook(book) {
    return HTTP.delete(BASE_URL + "/books/" + book.id, {
      headers: authHeader(),
    }).then(
      (response) => {
        return response.data;
      },
      (error) => {
        alert(error.response.data.message);
      }
    );
  },

  generateCSV() {
    return axios({
      url: BASE_URL + '/books/export/CSV',
      method: 'GET',
      headers: authHeader(),
      responseType: 'blob',
    }).then((response) => {
      var fileURL = window.URL.createObjectURL(new Blob([response.data]));
      var fileLink = document.createElement('a');

      fileLink.href = fileURL;
      fileLink.setAttribute('download', 'report.csv');
      document.body.appendChild(fileLink);

      fileLink.click();
    });
  },
  generatePDF() {
    return axios({
      url: BASE_URL + '/books/export/PDF',
      method: 'GET',
      headers: authHeader(),
      responseType: 'blob',
    }).then((response) => {
      var fileURL = window.URL.createObjectURL(new Blob([response.data]));
      var fileLink = document.createElement('a');

      fileLink.href = fileURL;
      fileLink.setAttribute('download', 'report.pdf');
      document.body.appendChild(fileLink);

      fileLink.click();
    });
  },
};
