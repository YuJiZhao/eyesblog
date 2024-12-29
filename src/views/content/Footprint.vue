<template>
    <div class="footprint" id="footprint"></div>
</template>

<script lang="ts">
import { defineComponent, onActivated, ref, inject, onMounted } from "vue";
import useProcessControl from "@/hooks/useProcessControl";
import { ApiObject, ProcessInterface } from "@/d.ts/plugin";
import { codeConfig, contextConfig } from "@/config/program";
import { useRouter } from "vue-router";
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'

export default defineComponent({
    name: "Footprint",
    components: {},
    setup() {
        const $api = inject<ApiObject>("$api")!;
        const $process = inject<ProcessInterface>("$process")!;
        let router = useRouter();
        let map = ref<any>();

        function initMap() {
            $api.getContextItem([contextConfig.footprintInit]).then(({ code, msg, data }) => {
                if (code == codeConfig.success) {
                    // 初始化地图
                    let config = JSON.parse(data.content);
                    map.value = L.map("footprint", JSON.parse(config.mapInit));
                    L.tileLayer(
                        config.layer,
                        {
                            attribution: config.attribution,
                        }
                    ).addTo(map.value);

                    // 获取并填充足迹信息
                    getFootprint();
                } else {
                    $process.tipShow.error("地图初始化失败！" + msg);
                }
            })
        }

        function getFootprint() {
            $api.getFootprintList().then(({ code, msg, data }) => {
                if (code == codeConfig.success) {
                    data.forEach((element: any) => {
                        addMark(element.id, element.city, element.latitude, element.longitude);
                    });
                } else {
                    $process.tipShow.error("获取足迹信息失败！" + msg);
                }
            })
        }

        function addMark(id: number, city: string, latitude: number, longitude: number) {
            var labelMarker = L.marker([latitude, longitude], {
                zIndexOffset: 0,
                riseOnHover: true,
                title: city,
            });
            labelMarker.on("click", (e: any) => {
                console.log(e);
                window.open(router.resolve(`/footprint/details/${id}`).href, "_blank");
            });
            labelMarker.addTo(map.value);
        }

        onActivated(() => {
            useProcessControl(false, false, false);
        });

        onMounted(() => {
            initMap();
        })

        return {};
    },
});
</script>

<style lang="scss" scoped>
#footprint {
    position: fixed;
    top: 0;
    left: 0;
    margin: 0;
    width: 100vw;
    height: 100vh;
}
</style>