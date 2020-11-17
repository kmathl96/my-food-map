<template>
  <div>
    <RestaurantCard 
      :restaurantInfo="restaurant"
    />
  </div>

</template>

<script>
import RestaurantCard from '@/components/restaurant/RestaurantCard';
import RestaurantApi from '@/api/RestaurantApi.js'

export default {
  name: 'Restaurant',
  components: {
    RestaurantCard,
  },
  data() {
    return {
      restaurant: {},
    }
  },
  methods: {
    setRestaurant() {
      const restaruantId = this.$route.params.restaurantId
      //console.log("realSetData 요청 Id 값 : ", restaruantId, typeof(restaruantId))
      RestaurantApi.requestInfo(
        restaruantId,
        res => {
         // console.log("realSetData 콜백 성공, res:", res.data.message)
          this.restaurant = res.data.message
        },
        err => {
          console.error(err)
          //console.log("에러반응")
        }
      )
    },
  },
  mounted() {
    this.setRestaurant()
  },
}
</script>
<style>

</style>