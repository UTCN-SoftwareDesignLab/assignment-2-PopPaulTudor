import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/books", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  createItem(item) {
    return HTTP.post(BASE_URL + "/books", item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  editItem(item) {
    return HTTP.patch(BASE_URL + "/books", item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
