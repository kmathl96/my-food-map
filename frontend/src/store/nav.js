export default {
  namespaced: true,

  state: {
    items: [],
  },
  mutations: {
    setItems (state, data) {
      state.items = data
      // console.log("setItems 작동")
    }
  },
}
  