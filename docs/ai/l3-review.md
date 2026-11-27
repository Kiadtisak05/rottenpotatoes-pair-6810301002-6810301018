# Review of docs/ai/l3-draft.md
Reviewers: Kiadtisak05 and Nightlynight24. Checked against docs/intent/movie-filter.md and SMART.

| บรรทัดในร่าง | ปัญหา | ตัดสิน: แก้ ตัด หรือเก็บ |
|---|---|---|
| 17: "REQ-2 The result shall contain only movies with the chosen rating" | ตรงกับ Proposed outcome และทดสอบได้ด้วย Then ที่ว่าไม่เห็นหนัง rating อื่น | เก็บ |
| 18: "REQ-3 The result shall contain every movie in the catalog that has the chosen rating" | ตรงกับ Proposed outcome ผู้ปกครองต้องเห็นหนังครบทุกเรื่องของ rating นั้น | เก็บ |
| 20: "REQ-5 The result shall be ordered with newer movies before older ones" | ลำดับหนังใหม่ก่อนเป็นเรื่องของบัตรใบที่ 2 ไม่ใช่เงื่อนไขของการกรอง ถ้าผูกไว้กับบัตรใบที่ 1 บัตรจะใหญ่เกิน iteration | แก้: ย้ายไปเป็นเกณฑ์ของบัตรใบที่ 2 "the movie list shows the newest release first" บัตรใบที่ 1 ไม่ตรวจลำดับ |
| 21: "REQ-6 Each movie in the result shall carry enough data (at least its title and rating)" | ตรงกับ "without opening any movie" ใน intent และ Lo-Fi แสดงชื่อ rating และปีในแต่ละแถว | เก็บ |
| 22: "REQ-7 The operation shall not change the catalog ... contents and order are identical before and after the call" | ลูกค้าไม่ได้ขอ และผู้ปกครองมองไม่เห็น "before and after the call" เป็นคำของโค้ด | ตัด |
| 24: "REQ-9 ... results never include movies of a different rating, whatever the child's age" | ซ้ำกับ REQ-2 และระบบไม่มีอายุของเด็กเลย (intent บอกว่าไม่มี age profile) | ตัด |
| 25: "REQ-10 ... return an empty list and shall not fail ... no exception" | ตอบ Open question ข้อ 1 แทนลูกค้า และ "exception" เป็นคำของโค้ด ผู้ปกครองไม่เห็น | แก้: เป็นคำถามถึง customer proxy ว่าอยากเห็นอะไรเมื่อไม่มีหนัง แล้วเขียนตามที่ยืนยัน: เห็น "No movies rated G" และไม่มีหนังในรายการ (บัตรใบที่ 3) |
| 26: "REQ-11 A missing rating (null) or a value that is not one of the five ratings shall be rejected" | ลูกค้าไม่ได้ขอ บนหน้าจอผู้ปกครองกดได้เพียง 5 ปุ่ม (Lo-Fi) จึงเลือก rating ว่างหรือผิดไม่ได้ และ null เป็นคำของโค้ด | ตัด |
| 27: "REQ-12 The feature shall be implemented in plain Java with no database" | เป็นข้อจำกัดของทีม ไม่ใช่สิ่งที่ผู้ใช้เห็นหรือทดสอบได้ อยู่ใน Constraints ของ intent แล้ว | ตัด |
| 31-34: Story 1 "As a parent of an 8-year-old ... I want to choose one rating and see only the movies with that rating" | ตรงกับ Problem และ Proposed outcome ครบสามบรรทัด Connextra | เก็บ (บัตรใบที่ 1 ใช้ "concerned parent" ตาม feature) |
| 42-45: Story 2 "As a parent ... I want the filtered movies to be listed newest first" | ไม่ตรงกับบัตรใบที่ 2 ที่ customer proxy ยืนยัน การเรียงหนังใหม่ก่อนมีประโยชน์กับผู้ใช้ทุกคน และใช้กับรายการหนังทั้งหมด ไม่ใช่เฉพาะผลการกรอง | แก้: ใช้ข้อความบนบัตรใบที่ 2 "As a moviegoer / So that I can find recent movies without scrolling / I want the movie list to show the newest release first" |
| 52-55: Story 3 "No account needed, no data kept" | ไม่ใช่ story ใหม่ เป็น Constraint ที่มีใน intent อยู่แล้ว ร่างเองก็ยอมรับในบรรทัด 125 | แก้: เอาออกจากบัตร ใช้บัตรใบที่ 3 เป็น sad path ของบัตรใบที่ 1 แทน |
| 78-83: Happy path "Movie A ... Movie D" และ "Movie B is listed before Movie C" | ข้อมูลไม่ตรงกับ movie_filter.feature และบรรทัดลำดับเป็นเรื่องของบัตรใบที่ 2 | แก้: ใช้ Background "Men In Black" (PG-13, 1997) และ "Alien" (R, 1979) เลือก PG-13 แล้วเห็น Men In Black ไม่เห็น Alien ไม่ตรวจลำดับ |
| 85-88: Sad path "chooses the rating "NC-17"" แล้ว "the result is an empty list" และ "no error is raised" | ไม่ตรงกับบัตรใบที่ 3 และ Lo-Fi จอ 3 ที่ใช้ G และ "error" เป็นคำของโค้ด ผู้ปกครองไม่เห็น | แก้: เลือก G แล้วเห็น "No movies rated G" และไม่มีหนังในรายการ ใน movie_filter.feature ใช้ step "I should see no movies" |
| 90-93: Scenario "Sad path - no rating is chosen" | ลูกค้าไม่ได้ขอ หน้าจอไม่มีทางกดค้นหาโดยไม่เลือก rating | ตัด |
| 95-98: Scenario "a rating that is not one of the five labels" ("PG-18") | ลูกค้าไม่ได้ขอ ผู้ปกครองพิมพ์ rating เองไม่ได้ มีแค่ 5 ปุ่ม | ตัด |
| 103: "A1 (answers open question 1) ... the result is an empty list, not an error" | ตอบ Open question แทนลูกค้า ร่างเขียนเองว่า "answers open question" | แก้: เป็นคำถามถึง customer proxy (ยืนยันแล้วตามบัตรใบที่ 3) |
| 104: "A2 (answers open question 3) "Newest" means the movie's release date" | ตอบ Open question แทนลูกค้า | แก้: เป็นคำถามถึง customer proxy ก่อนยืนยันบัตรใบที่ 2 |
| 105: "A3 Movies with the same release date are ordered by title A to Z" | ลูกค้าไม่ได้ขอ เป็นการตัดสินแทนทีมและลูกค้า | ตัด |
| 106: "A4 (answers open question 2) The parent chooses exactly one rating per request" | ตอบ Open question ข้อ 2 แทนลูกค้า และ "per request" เป็นคำของโค้ด | แก้: เป็นคำถามถึง customer proxy ว่าเลือกหลาย rating พร้อมกันได้ไหม |
| 112: "A10 Movies with a missing release date are placed after all dated movies" | ลูกค้าไม่ได้ขอ และ Movie ของ L2 ไม่ยอมให้วันที่ว่างอยู่แล้ว | ตัด |
| 116: "Wrong child-safety expectation ... The tool does not judge suitability" | ข้อกังวลจริง rating ไม่ได้แปลว่าเหมาะกับเด็กทุกคน และสอดคล้องกับ intent ที่ไม่มีระบบแนะนำ | เก็บ (เป็นข้อควรระวังของหน้าเว็บในอนาคต) |

Kept: REQ-2 (บรรทัด 17) เพราะตรงกับ Proposed outcome และวัดผลได้ทันทีด้วย Then "I should not see" ใน movie_filter.feature
Counts: แก้ 9 ตัด 8 เก็บ 5
Customer proxy: Nightlynight24 (B, in role as the parent of an 8-year-old) confirmed the three cards on 2026-11-26
