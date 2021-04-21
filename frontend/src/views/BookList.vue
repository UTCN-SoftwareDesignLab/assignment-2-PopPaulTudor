<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headersBooks"
      :items="booksItem"
      :search="search"
      @click:row="editBook"
    ></v-data-table>
    <BookDialog
      :opened="bookDialogVisibility"
      :book="bookSelected"
      @refresh="refreshList"
    ></BookDialog>
    <v-text-field v-model="searchTitle" label="Title" />
    <v-text-field v-model="searchAuthor" label="Author" />
    <v-text-field v-model="searchGenre" label="Genre" />
    <v-btn @click="addBook">Add Book</v-btn>
    <v-btn @click="pdfReport">Generate PDF Report</v-btn>
    <v-btn @click="csvReport">Generate CSV Report</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "@/components/BookDialog";

export default {
  name: "BookList",

  components: { BookDialog },
  data() {
    return {
      booksItem: [],
      search: "",
      searchTitle: "",
      searchAuthor: "",
      searchGenre: "",
      headersBooks: [
        { text: "ID", align: "start", sortable: false, value: "id" },
        { text: "Title", value: "title" },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
        { text: "Price", value: "price" },
      ],
      bookDialogVisibility: false,
      bookSelected: {},
    };
  },
  methods: {
    addBook() {
      this.bookDialogVisibility = true;
    },

    editBook(book) {
      this.bookSelected = book;
      this.bookDialogVisibility = true;
    },

    pdfReport() {
      api.books.generatePDF();
    },
    csvReport() {
      api.books.generateCSV();
    },
    async searchBooks() {
      this.bookDialogVisibility = false;
      this.bookSelected = {};
    },
    async refreshList() {
      this.booksItem = await api.books.getBooks();
      this.bookDialogVisibility = false;
      this.bookSelected = {};
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
