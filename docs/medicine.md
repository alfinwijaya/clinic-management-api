# Medicine API Spec

## Get Medicine

Endpoint : GET /api/medicines

Query Parameters

| Parameter        | Type   | Description                                    | Required | Default | Example           |
|------------------|--------|------------------------------------------------|----------|---------|-------------------|
| `page`           | String | The page number to retrieve.                  | No       | `1`     | `1`               |
| `size`           | String | The number of items per page.                 | No       | `10`    | `10`              |
| `from_date`      | Date   | The start date for filtering (inclusive).     | No       | `null`  | `2024-01-01`      |
| `to_date`        | Date   | The end date for filtering (inclusive).       | No       | `null`  | `2024-12-31`      |
| `farmalkes_type` | String | The type of farmalkes.                        | No       | `null`  | `type1`           |
| `keyword`        | String | A keyword to search for in medicine names.    | No       | `null`  | `keyword1`        |
| `template_code`  | String | The template code for filtering medicines.    | No       | `null`  | `template1`       |
| `packaging_code` | String | The packaging code for filtering medicines.   | No       | `null`  | `packaging1`      |

Response Body (Success) :

```json
{
  "data": {
    "total": 19418,
    "page": 1,
    "size": 1,
    "items": {
      "data": [
        {
          "name": "Paracetamol 250 mg Sirup (OBAPA)",
          "kfa_code": "92001142",
          "active": false,
          "state": "valid",
          "image": null,
          "updated_at": "2023-09-14 03:36:32",
          "farmalkes_type": {
            "code": "medicine",
            "name": "Obat",
            "group": "farmasi"
          },
          "dosage_form": {
            "code": "BS055",
            "name": "Sirup"
          },
          "produksi_buatan": "lokal",
          "nie": "DBL1431900837B1",
          "nama_dagang": "OBAPA",
          "manufacturer": "TRIYASA NAGAMAS FARMA",
          "registrar": "TRIYASA NAGAMAS FARMA",
          "generik": false,
          "rxterm": 525,
          "dose_per_unit": 1,
          "fix_price": null,
          "het_price": null,
          "farmalkes_hscode": null,
          "tayang_lkpp": null,
          "kode_lkpp": null,
          "net_weight": null,
          "net_weight_uom_name": "g",
          "volume": null,
          "volume_uom_name": "mL",
          "med_dev_jenis": null,
          "med_dev_subkategori": null,
          "med_dev_kategori": null,
          "med_dev_kelas_risiko": null,
          "klasifikasi_izin": null,
          "uom": {
            "name": "Botol Plastik"
          },
          "product_template": {
            "name": "Paracetamol 250 mg Sirup",
            "state": "valid",
            "active": false,
            "kfa_code": "92001142",
            "updated_at": "2024-03-23 04:36:09",
            "display_name": "Paracetamol 250 mg Sirup"
          },
          "active_ingredients": [
            {
              "state": "valid",
              "active": true,
              "kfa_code": "91000101",
              "zat_aktif": "Paracetamol",
              "updated_at": "2022-10-11 07:10:04",
              "kekuatan_zat_aktif": "250 mg"
            }
          ],
          "tags": [
            {
              "code": null,
              "name": null
            }
          ],
          "replacement": {
            "product": {
              "name": "Paracetamol 250 mg/5 mL Sirup",
              "reason": "Duplikat",
              "kfa_code": "93003161"
            },
            "template": {
              "name": "Paracetamol 250 mg/5 mL Sirup",
              "reason": "Duplikat",
              "kfa_code": "92001121"
            }
          }
        }
      ]
    }
  },
  "errors": null
}
```