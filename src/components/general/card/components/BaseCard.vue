<template>
  <div class="card baseCard">
    <slot />
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.baseCard {
    width: 100%;
    padding: 20px;
    box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    -webkit-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
    -moz-box-shadow: 0 0 3px rgba($color: $black, $alpha: 0.8);
}
</style>
