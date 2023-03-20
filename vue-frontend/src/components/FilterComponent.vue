<template>
    <div class = "filter-wrapper">
        <div class = "price">
            <label>Price</label>
            <div class = "price-input">
                <div>
                    <input type="number" id="min-price" name ="min-price">
                    <label for="min-price">From</label>
                </div>
                <div>
                    <input type="number" id="max-price" name ="max-price">
                    <label for="max-price">To</label>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
</script>


<style scoped>
    .filter-wrapper{
        width: 200px;
        background-color: rgba(30, 29, 29, 0.99);
        color: #ffffff;
        padding: 12px 16px;
        border-radius: 4px;
        border: 1px solid #646464;
        font-size: 14px;
        cursor: pointer;
        text-align: left;
        align-content: center;
    }
    .price-input{
        display: inline-flex;
        margin: 3%;
    }
    .price-input input{
        width: 97%;
    }

</style>