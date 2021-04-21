<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create book" : "Edit book" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-if="!isNew" v-model="book.id" label="ID" readonly />
          <v-text-field v-model="book.title" label="Title" />
          <v-text-field v-model="book.author" label="Author" />
          <v-text-field v-model="book.genre" label="Genre" />
          <v-text-field v-model="book.quantity" label="Quantity" number />
          <v-text-field v-model="book.price" label="Price" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persistBook">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" v-on:click="delBook">Delete</v-btn>
          <v-btn v-if="!isNew" v-on:click="sellBook">Sell Book</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    persistBook() {
      if (this.isNew) {
        api.books
          .createBook({
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.books
          .editBook({
            id: this.book.id,
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .then(() => this.$emit("refresh"));
      }
    },

    delBook() {
      api.books
        .deleteBook({
          id: this.book.id,
        })
        .then(() => this.$emit("refresh"));
    },

    sellBook() {
      api.books
        .editBook({
          id: this.book.id,
          title: this.book.title,
          author: this.book.author,
          genre: this.book.genre,
          quantity: this.book.quantity,
          price: this.book.price,
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.book.id;
    },
  },
};
</script>

<style scoped></style>
