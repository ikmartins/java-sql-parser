select t0.x, t1.y yy, avg(q8.v) from t0, t1
inner join t2 on 1
left join t3 on 2
left outer join t4 on 1
right join t5 on 1
right outer join t6 on 1
cross join t7
inner join (select * from t8, t9 inner join t0 on 1 where x=y) q8 on 1
where x=y
group by t0.x, t1.y
having max(t3.z) < 100
order by 2 desc, 1 asc;

